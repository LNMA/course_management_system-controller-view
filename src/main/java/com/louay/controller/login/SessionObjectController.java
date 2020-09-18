package com.louay.controller.login;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.role.AccountsRoles;
import com.louay.model.entity.role.UsersRoles;
import com.louay.model.entity.users.Admin;
import com.louay.model.entity.users.constant.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
public class SessionObjectController implements Serializable {
    private static final long serialVersionUID = -8516342061848503798L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public SessionObjectController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @GetMapping(value = "/session_id", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getSessionId(@SessionAttribute(value = "id", required = false) String sessionEmail) {
        if (sessionEmail == null) {
            redirectToLoginPage();
        }
        return sessionEmail;
    }

    @GetMapping(value = "/session_object/get_role_user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Role getUserRole(@SessionAttribute(value = "id", required = false) String emailInSession) {
        if (emailInSession == null) {
            redirectToLoginPage();
        }

        return findAccountsRoles(emailInSession).getRoleName();
    }

    private AccountsRoles findAccountsRoles(String email) {
        AccountsRoles accountsRoles = buildAccountsRoles(email);

        return this.servicesFactory.getRoleService().findAccountRoleByRoleId(accountsRoles);
    }

    private AccountsRoles buildAccountsRoles(String email) {
        UsersRoles usersRoles = findUsersRoles(email);
        AccountsRoles accountsRoles = this.entitiesFactory.getAccountsRoles();
        accountsRoles.setRoleID(usersRoles.getAccountsRoles().getRoleID());

        return accountsRoles;
    }

    private UsersRoles findUsersRoles(String email) {
        UsersRoles usersRoles = buildUsersRoles(email);

        return this.servicesFactory.getRoleService().findUsersRolesByUserId(usersRoles);
    }

    private UsersRoles buildUsersRoles(String email) {
        UsersRoles usersRoles = this.entitiesFactory.getUsersRoles();
        Admin admin = buildAdmin(email);
        usersRoles.setUsers(admin);
        usersRoles.setAccountsRoles(this.entitiesFactory.getAccountsRoles());

        return usersRoles;
    }

    private Admin buildAdmin(String email) {
        Admin admin = this.entitiesFactory.getAdmin();
        admin.setEmail(email);

        return admin;
    }

    @GetMapping(value = "/session_object-redirect-login")
    public String redirectToLoginPage() {
        return "redirect:/login";
    }

}
