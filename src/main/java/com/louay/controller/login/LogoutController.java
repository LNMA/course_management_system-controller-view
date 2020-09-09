package com.louay.controller.login;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.controller.util.filter.EmailFilter;
import com.louay.model.entity.status.UserAccountStatus;
import com.louay.model.entity.status.UserAtCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/logout")
public class LogoutController implements Serializable {
    private static final long serialVersionUID = -2125032391350724202L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;
    private final EmailFilter emailFilter;

    @Autowired
    public LogoutController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory, EmailFilter emailFilter) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");
        Assert.notNull(emailFilter, "emailFilter cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
        this.emailFilter = emailFilter;
    }

    @RequestMapping(value = "/logout-account/{email}")
    public ResponseEntity<String> logoutUser(@PathVariable(value = "email", required = false) String email,
                                     HttpServletRequest request, HttpServletResponse response) {
        if (email == null) {
            return ResponseEntity.ok().body("/login");
        }

        String originalEmail = this.emailFilter.filterEmailUrlToOriginal(email);

        updateUserAccountStatusToOffline(originalEmail);

        if (request.getSession(false) != null) {
            deleteSessionLogin(request);
        }

        if (request.getCookies() != null) {
            deleteCookies(request, response);
        }

        return ResponseEntity.ok().body("/login");
    }

    private void deleteSessionLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setAttribute("id", null);
        session.setAttribute("password", null);
        session.removeAttribute("id");
        session.removeAttribute("password");
    }

    private void deleteCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName() != null) {
                    switch (c.getName()) {
                        case "ID-LOGIN":
                        case "SECURE_NUMBER-LOGIN":
                        case "JSESSIONID":
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

    private void updateUserAccountStatusToOffline(String email) {
        UserAccountStatus userAccountStatus = findUserAccountStatus(email);
        userAccountStatus.setOnline(false);

        this.servicesFactory.getStatusService().updateUserAccountStatus(userAccountStatus);
    }

    private UserAccountStatus findUserAccountStatus(String email) {
        UserAccountStatus userAccountStatus = buildUserAccountStatus(email);

        userAccountStatus = this.servicesFactory.getStatusService()
                .findUserAccountStatusByUserId(userAccountStatus);

        return userAccountStatus;
    }

    private UserAccountStatus buildUserAccountStatus(String email) {
        UserAccountStatus userAccountStatus = this.entitiesFactory.getUserAccountStatus();
        userAccountStatus.setUsers(this.entitiesFactory.getUsers());
        userAccountStatus.getUsers().setEmail(email);

        return userAccountStatus;
    }

    @RequestMapping(value = "/exit-course/{courseId}")
    public String exitFromCourse(@PathVariable(value = "courseId") String courseId,
                                 @SessionAttribute(value = "id") String email) {
        if (courseId == null || email == null) {
            return "redirect:/login";
        }

        UserAtCourse userAtCourse = findUserAtCourse(email);
        userAtCourse.setBusy(false);
        updateUserAtCourseToFree(userAtCourse);

        return "redirect:/login";
    }

    private void updateUserAtCourseToFree(UserAtCourse userAtCourse){
        this.servicesFactory.getStatusService().updateUserAtCourse(userAtCourse);
    }

    private UserAtCourse findUserAtCourse(String email){
        UserAtCourse userAtCourse = buildUserAtCourse(email);
        return this.servicesFactory.getStatusService().findUserAtCourseByUserId(userAtCourse);
    }

    private UserAtCourse buildUserAtCourse(String email){
        UserAtCourse userAtCourse = this.entitiesFactory.getUserAtCourse();
        userAtCourse.setUsers(this.entitiesFactory.getUsers());
        userAtCourse.getUsers().setEmail(email);

        return userAtCourse;
    }
}
