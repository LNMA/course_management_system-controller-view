package com.louay.controller.login;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.controller.factory.WrappersFactory;
import com.louay.model.entity.authentication.CookieLogin;
import com.louay.model.entity.role.AccountsRoles;
import com.louay.model.entity.role.UsersRoles;
import com.louay.model.entity.status.UserAccountStatus;
import com.louay.model.entity.status.UserSignIn;
import com.louay.model.entity.users.Admin;
import com.louay.model.entity.users.Users;
import com.louay.model.entity.wrapper.AdminRememberMeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.io.Serializable;
import java.security.SecureRandom;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/login")
public class LoginController implements Serializable {
    private static final long serialVersionUID = -7796548474342711567L;
    private final ServicesFactory servicesFactory;
    private final EntitiesFactory entitiesFactory;
    private final WrappersFactory wrappersFactory;
    private final PasswordEncoder passwordEncoder;
    private String urlEmail;
    private AccountsRoles accountsRoles;

    @Autowired
    public LoginController(ServicesFactory servicesFactory, EntitiesFactory entitiesFactory,
                           PasswordEncoder passwordEncoder, WrappersFactory wrappersFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null!.");
        Assert.notNull(wrappersFactory, "wrappersFactory cannot be null!.");

        this.servicesFactory = servicesFactory;
        this.entitiesFactory = entitiesFactory;
        this.passwordEncoder = passwordEncoder;
        this.wrappersFactory = wrappersFactory;
    }

    @GetMapping
    public String viewLoginPage(@SessionAttribute(value = "id", required = false) String sessionEmail,
                                @SessionAttribute(value = "password", required = false) String sessionPassword,
                                @CookieValue(value = "ID-LOGIN", required = false) String cookieEmail,
                                @CookieValue(value = "SECURE_NUMBER-LOGIN", required = false) String cookieSecureNumber,
                                PushBuilder pushBuilder) {
        if (sessionEmail != null && sessionPassword != null) {
            if (sessionEmail.length() > 7 && sessionPassword.length() > 7) {
                return "redirect:/login/perform_session_login";
            }
        }

        if (cookieEmail != null && cookieSecureNumber != null) {
            if (cookieEmail.length() > 7 && cookieSecureNumber.length() > 7) {
                return "redirect:/login/perform_cookie_login";
            }
        }

        if (pushBuilder != null) {
            pushBuilder
                    .path("/static/images/background_geometry.png")
                    .path("/static/css/small_form.css")
                    .path("/static/css/background-large_form.css")
                    .path("/static/lib/bootstrap-4.5.1/css/bootstrap.min.css")
                    .path("/static/images/favicon.ico")
                    .path("/static/js/service/login_submit.js")
                    .path("/static/js/control/login-controller.js")
                    .path("/static/js/app.js")
                    .path("/static/lib/angularJS-1.8.0/angular-messages.min.js")
                    .path("/static/lib/angularJS-1.8.0/angular.min.js")
                    .path("/static/lib/popper-2.4.3/popper.min.js")
                    .path("/static/lib/bootstrap-4.5.1/js/bootstrap.min.js")
                    .path("/static/lib/jQuery-3.5.1/jquery-3.5.1.min.js")
                    .push();
        }
        return "/static/html/login.html";
    }

    @RequestMapping(value = "/perform_session_login")
    public String loginSessionProcess(@SessionAttribute(value = "id", required = false) String sessionEmail,
                                      @SessionAttribute(value = "password", required = false) String sessionPassword,
                                      HttpServletRequest request) {
        if (sessionEmail == null || sessionPassword == null) {
            return "redirect:/login";
        }
        AdminRememberMeWrapper adminWrapper = buildAdminRememberMeWrapper(sessionEmail);
        adminWrapper.getAdmin().setPassword(sessionPassword);

        if (!isAccountExist(adminWrapper)) {
            deleteSessionLogin(request);
            return "redirect:/login";
        }
        if (!isEmailAndPasswordSessionMatched(adminWrapper)) {
            deleteSessionLogin(request);
            return "redirect:/login";
        }
        UserAccountStatus userAccountStatus = findUserAccountStatus(adminWrapper);
        if (!userAccountStatus.getValid()) {
            deleteSessionLogin(request);
            return "redirect:/login";
        }

        updateUserAccountStatusToOnline(adminWrapper);

        createNewSessionForSessionLogin(request, adminWrapper);

        this.accountsRoles = findAccountRoles(adminWrapper);
        this.urlEmail = adminWrapper.getAdmin().getEmail();
        return "redirect:/login/redirect_tracer_success_login";
    }

    private Boolean isEmailAndPasswordSessionMatched(AdminRememberMeWrapper adminWrapper) {
        Admin admin = findAdminByEmail(adminWrapper);

        return admin.getEmail().equalsIgnoreCase(adminWrapper.getAdmin().getEmail()) &&
                admin.getPassword().equals(adminWrapper.getAdmin().getPassword());
    }

    private void deleteSessionLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setAttribute("id", null);
        session.setAttribute("password", null);
        session.removeAttribute("id");
        session.removeAttribute("password");
    }

    @RequestMapping(value = "/perform_cookie_login")
    public String loginCookieProcess(@CookieValue(value = "ID-LOGIN", required = false) String cookieEmail,
                                     @CookieValue(value = "SECURE_NUMBER-LOGIN", required = false) String cookieSecureNumber,
                                     HttpServletRequest request, HttpServletResponse response) {
        if (cookieEmail == null || cookieSecureNumber == null) {
            return "redirect:/login";
        }
        Integer secureNumber = Integer.parseInt(cookieSecureNumber);
        AdminRememberMeWrapper adminWrapper = buildAdminRememberMeWrapper(cookieEmail);

        if (!isAccountExist(adminWrapper)) {
            deleteCookies(request, response);
            return "redirect:/login";
        }
        if (!isEmailAndSecureNumberMatched(adminWrapper, secureNumber)) {
            deleteCookies(request, response);
            return "redirect:/login";
        }

        UserAccountStatus userAccountStatus = findUserAccountStatus(adminWrapper);
        if (!userAccountStatus.getValid()) {
            deleteCookies(request, response);
            return "redirect:/login";
        }
        /*
        if (userAccountStatus.getOnline()) {
            deleteCookies(request, response);
            return "redirect:/login";
        }*/

        createSignInDate(adminWrapper);
        updateUserAccountStatusToOnline(adminWrapper);

        createNewSessionCookieLogin(request, adminWrapper);

        this.accountsRoles = findAccountRoles(adminWrapper);
        this.urlEmail = adminWrapper.getAdmin().getEmail();

        return "redirect:/login/redirect_tracer_success_login";
    }

    @RequestMapping(value = "/redirect_tracer_success_login")
    private String getTracerAccountHomePage() {
        switch (this.accountsRoles.getRoleName()) {
            case STUDENT:
                return String.format("redirect:/student/student_home/%s", this.urlEmail);
            case INSTRUCTOR:
                return String.format("redirect:/instructor/instructor_home/%s", this.urlEmail);
            case ADMIN:
                return String.format("redirect:/admin/admin_home/%s", this.urlEmail);
            default:
                throw new UnsupportedOperationException("No roles had matched!.");
        }
    }

    private void deleteCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName() != null) {
                    switch (c.getName()) {
                        case "ID-LOGIN":
                        case "SECURE_NUMBER-LOGIN":
                            c.setValue(null);
                            c.setMaxAge(0);
                            c.setPath("/");
                            response.addCookie(c);
                            break;
                    }
                }
            }
        }
    }

    private AdminRememberMeWrapper buildAdminRememberMeWrapper(String email) {
        AdminRememberMeWrapper adminWrapper = this.wrappersFactory.getAdminRememberMeWrapper();
        adminWrapper.setAdmin(this.entitiesFactory.getAdmin());
        adminWrapper.getAdmin().setEmail(email);

        return adminWrapper;
    }

    private Boolean isEmailAndSecureNumberMatched(AdminRememberMeWrapper adminWrapper, Integer secureNumber) {
        Admin admin = findAdminByEmail(adminWrapper);
        CookieLogin cookieLogin = this.entitiesFactory.getCookieLogin();
        cookieLogin.setAdmin(admin);
        cookieLogin = this.servicesFactory.getAuthenticationService().findCookieLoginByEmail(cookieLogin);

        return admin.getEmail().equalsIgnoreCase(adminWrapper.getAdmin().getEmail()) &&
                secureNumber.equals(cookieLogin.getSecureCode());
    }

    @PostMapping(value = "/perform_login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String loginProcess(@RequestBody AdminRememberMeWrapper adminWrapper, HttpServletResponse response,
                               HttpServletRequest request) {
        if (adminWrapper.getAdmin().getEmail() == null || adminWrapper.getAdmin().getPassword() == null) {
            throw new IllegalArgumentException("email and password cannot be null!.");
        }
        if (!isAccountExist(adminWrapper)) {
            return "forward:/login/account_not_exist";
        }
        if (!isEmailAndPasswordMatched(adminWrapper)) {
            return "forward:/login/account_not_matched";
        }

        UserAccountStatus userAccountStatus = findUserAccountStatus(adminWrapper);
        if (!userAccountStatus.getValid()) {
            return "forward:/login/account_not_valid";
        }
        if (userAccountStatus.getOnline()) {
            return "forward:/login/account_is_online";
        }

        if (adminWrapper.isRememberMe()) {
            Cookie emailCookie = buildEmailCookie(adminWrapper);
            Cookie secureNumberCookie = buildSecureNumberCookie(adminWrapper);
            response.addCookie(emailCookie);
            response.addCookie(secureNumberCookie);
        }

        createSignInDate(adminWrapper);
        updateUserAccountStatusToOnline(adminWrapper);

        createNewSession(request, adminWrapper);

        this.accountsRoles = findAccountRoles(adminWrapper);
        this.urlEmail = adminWrapper.getAdmin().getEmail();

        return "forward:/login/redirect_success_login";
    }

    @RequestMapping(value = "/redirect_success_login")
    private ResponseEntity<String> getAccountHomePage() {
        switch (this.accountsRoles.getRoleName()) {
            case STUDENT:
                return ResponseEntity.ok().body(String.format("/student/student_home/%s", this.urlEmail));
            case INSTRUCTOR:
                return ResponseEntity.ok().body(String.format("/instructor/instructor_home/%s", this.urlEmail));
            case ADMIN:
                return ResponseEntity.ok().body(String.format("/admin/admin_home/%s", this.urlEmail));
            default:
                throw new UnsupportedOperationException("No roles had matched!.");
        }
    }

    @PostMapping(value = "/account_not_exist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> accountNotExist() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found!.");
    }

    private Boolean isAccountExist(AdminRememberMeWrapper adminRememberMeWrapper) {
        return this.servicesFactory.getAccountService().isExistAccount(adminRememberMeWrapper.getAdmin());
    }

    @PostMapping(value = "/account_not_matched", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> accountEmailOrPasswordDidNoMatch() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email or password wrong!.");
    }

    private Boolean isEmailAndPasswordMatched(AdminRememberMeWrapper adminRememberMeWrapper) {
        Admin admin = findAdminByEmail(adminRememberMeWrapper);

        return admin.getEmail().equalsIgnoreCase(adminRememberMeWrapper.getAdmin().getEmail()) &&
                this.passwordEncoder.matches(adminRememberMeWrapper.getAdmin().getPassword(), admin.getPassword());
    }

    private Admin findAdminByEmail(AdminRememberMeWrapper adminWrapper) {
        return this.servicesFactory.getAccountService().findAccountByEmail(adminWrapper.getAdmin());
    }

    @PostMapping(value = "/account_not_valid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> accountNotValidStatus() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Account not valid!, please check email to " +
                "verify your account.");
    }

    @PostMapping(value = "/account_is_online", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> accountIsOnline() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Account now online!");
    }

    private void updateUserAccountStatusToOnline(AdminRememberMeWrapper adminRememberMeWrapper) {
        UserAccountStatus userAccountStatus = findUserAccountStatus(adminRememberMeWrapper);
        userAccountStatus.setOnline(true);

        this.servicesFactory.getStatusService().updateUserAccountStatus(userAccountStatus);
    }

    private UserAccountStatus findUserAccountStatus(AdminRememberMeWrapper adminRememberMeWrapper) {
        UserAccountStatus userAccountStatus = buildUserAccountStatus(adminRememberMeWrapper);

        userAccountStatus = this.servicesFactory.getStatusService()
                .findUserAccountStatusByUserId(userAccountStatus);
        return userAccountStatus;
    }

    private UserAccountStatus buildUserAccountStatus(AdminRememberMeWrapper adminRememberMeWrapper) {
        Users users = this.entitiesFactory.getUsers();
        users.setAdmin(adminRememberMeWrapper.getAdmin());
        users.setEmail(adminRememberMeWrapper.getAdmin().getEmail());
        UserAccountStatus userAccountStatus = this.entitiesFactory.getUserAccountStatus();
        userAccountStatus.setUsers(users);

        return userAccountStatus;
    }

    private AccountsRoles findAccountRoles(AdminRememberMeWrapper adminRememberMeWrapper) {
        UsersRoles usersRoles = findUsersRoles(adminRememberMeWrapper);

        AccountsRoles accountsRoles = buildAccountRole(usersRoles);

        accountsRoles = this.servicesFactory.getRoleService().findAccountRoleByRoleId(accountsRoles);

        return accountsRoles;
    }

    private AccountsRoles buildAccountRole(UsersRoles usersRoles) {
        AccountsRoles accountsRoles = this.entitiesFactory.getAccountsRoles();
        accountsRoles.setRoleID(usersRoles.getAccountsRoles().getRoleID());

        return accountsRoles;
    }

    private UsersRoles findUsersRoles(AdminRememberMeWrapper adminRememberMeWrapper) {
        UsersRoles usersRoles = buildUsersRoles(adminRememberMeWrapper);

        return this.servicesFactory.getRoleService().findUsersRolesByUserId(usersRoles);
    }

    private UsersRoles buildUsersRoles(AdminRememberMeWrapper adminRememberMeWrapper) {
        UsersRoles usersRoles = this.entitiesFactory.getUsersRoles();
        usersRoles.setUsers(adminRememberMeWrapper.getAdmin());

        return usersRoles;
    }

    private Cookie buildSecureNumberCookie(AdminRememberMeWrapper adminRememberMeWrapper) {
        CookieLogin cookieLogin = createCookieLogin(adminRememberMeWrapper);
        Cookie secureNumberCookie = new Cookie("SECURE_NUMBER-LOGIN", cookieLogin.getSecureCode().toString());
        secureNumberCookie.setMaxAge(60 * 60 * 24 * 30);
        secureNumberCookie.setSecure(true);
        secureNumberCookie.setHttpOnly(true);
        secureNumberCookie.setPath("/"); //FIXME: change domain

        return secureNumberCookie;
    }

    private CookieLogin createCookieLogin(AdminRememberMeWrapper adminRememberMeWrapper) {
        CookieLogin cookieLogin = buildCookieLogin(adminRememberMeWrapper);
        if (isCookieLoginExist(adminRememberMeWrapper)) {
            cookieLogin = this.servicesFactory.getAuthenticationService().updateCookieLogin(cookieLogin);
        } else {
            cookieLogin = this.servicesFactory.getAuthenticationService().createCookieLogin(cookieLogin);
        }
        return cookieLogin;
    }

    private Boolean isCookieLoginExist(AdminRememberMeWrapper adminRememberMeWrapper) {
        CookieLogin cookieLogin = this.entitiesFactory.getCookieLogin();
        cookieLogin.setAdmin(adminRememberMeWrapper.getAdmin());

        return this.servicesFactory.getAuthenticationService().isExistCookieLogin(cookieLogin);
    }

    private CookieLogin buildCookieLogin(AdminRememberMeWrapper adminRememberMeWrapper) {
        CookieLogin cookieLogin = this.entitiesFactory.getCookieLogin();
        cookieLogin.setAdmin(adminRememberMeWrapper.getAdmin());

        SecureRandom secureRandom = new SecureRandom();
        int secureNumber = secureRandom.nextInt(100000000);

        cookieLogin.setSecureCode(secureNumber);

        return cookieLogin;
    }

    private Cookie buildEmailCookie(AdminRememberMeWrapper adminRememberMeWrapper) {
        Cookie emailCookie = new Cookie("ID-LOGIN", adminRememberMeWrapper.getAdmin().getEmail());
        emailCookie.setMaxAge(60 * 60 * 24 * 30);
        emailCookie.setSecure(true);
        emailCookie.setHttpOnly(true);
        emailCookie.setPath("/"); //FIXME: change domain

        return emailCookie;
    }

    private void createSignInDate(AdminRememberMeWrapper adminRememberMeWrapper) {
        UserSignIn userSignIn = buildUserSignIn(adminRememberMeWrapper);

        this.servicesFactory.getStatusService().createUserSignIn(userSignIn);
    }

    private UserSignIn buildUserSignIn(AdminRememberMeWrapper adminRememberMeWrapper) {
        Users users = this.entitiesFactory.getUsers();
        users.setEmail(adminRememberMeWrapper.getAdmin().getEmail());
        UserSignIn userSignIn = this.entitiesFactory.getUserSignIn();
        userSignIn.setUsers(users);

        return userSignIn;
    }

    private void createNewSession(HttpServletRequest request, AdminRememberMeWrapper adminWrapper) {
        HttpSession session = request.getSession(true);
        session.setAttribute("id", adminWrapper.getAdmin().getEmail());
        Admin admin = findAdminByEmail(adminWrapper);
        session.setAttribute("password", admin.getPassword());
    }

    private void createNewSessionCookieLogin(HttpServletRequest request, AdminRememberMeWrapper adminWrapper) {
        HttpSession session = request.getSession(true);
        session.setAttribute("id", adminWrapper.getAdmin().getEmail());
        Admin admin = findAdminByEmail(adminWrapper);
        session.setAttribute("password", admin.getPassword());
    }

    private void createNewSessionForSessionLogin(HttpServletRequest request, AdminRememberMeWrapper adminWrapper) {
        HttpSession session = request.getSession(true);
        session.setAttribute("id", adminWrapper.getAdmin().getEmail());
        session.setAttribute("password", adminWrapper.getAdmin().getPassword());
    }
}
