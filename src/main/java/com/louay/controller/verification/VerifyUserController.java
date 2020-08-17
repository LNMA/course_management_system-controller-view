package com.louay.controller.verification;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.authentication.UsersAuthentication;
import com.louay.model.entity.status.UserAccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
public class VerifyUserController implements Serializable {
    private static final long serialVersionUID = 2585996224351865574L;
    private final ServicesFactory servicesFactory;
    private final EntitiesFactory entitiesFactory;

    @Autowired
    public VerifyUserController(ServicesFactory servicesFactory, EntitiesFactory entitiesFactory) {
        if (servicesFactory == null || entitiesFactory == null) {
            throw new IllegalArgumentException("factory cannot be null at VerifyUserController.class");
        }
        this.servicesFactory = servicesFactory;
        this.entitiesFactory = entitiesFactory;
    }

    @GetMapping(value = "/user_verify/{userId}/{verifyNumber}")
    public void verifyUserThenRedirectIt(@PathVariable String userId, @PathVariable String verifyNumber) {
        UsersAuthentication usersAuthentication = buildUsersAuthentication(userId);
        Integer verifyNumberInteger = Integer.parseInt(verifyNumber);
        if (verifyNumberInteger.equals(usersAuthentication.getVerificationNumber())) {
            updateValidUserAccountStatus(userId);
        }
    }

    private UsersAuthentication buildUsersAuthentication(String userId){
        UsersAuthentication usersAuthentication = this.entitiesFactory.getUsersAuthentication();
        usersAuthentication.setUserId(userId);
        usersAuthentication = this.servicesFactory.getAuthenticationService()
                .findUsersAuthenticationByUserId(usersAuthentication);
        return usersAuthentication;
    }

    private void updateValidUserAccountStatus(String userId){
        UserAccountStatus userAccountStatus = buildUserAccountStatus(userId);

        this.servicesFactory.getStatusService().updateUserAccountStatus(userAccountStatus);
    }

    private UserAccountStatus buildUserAccountStatus(String userId){
        UserAccountStatus userAccountStatus = this.entitiesFactory.getUserAccountStatus();
        userAccountStatus.setUsers(this.entitiesFactory.getUsers());
        userAccountStatus.getUsers().setEmail(userId);
        userAccountStatus = this.servicesFactory.getStatusService().findUserAccountStatusByUserId(userAccountStatus);
        userAccountStatus.setValid(true);

        return userAccountStatus;
    }

}
