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
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
public class SessionObjectController implements Serializable {
    private static final long serialVersionUID = -5225408155730964513L;
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
    public String getSessionId(@SessionAttribute(value = "id") String sessionEmail) {
        return sessionEmail;
    }

    @GetMapping(value = "/session_object/get_role_user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Role getUserRole(@SessionAttribute(value = "id") String emailInSession) {

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
}
