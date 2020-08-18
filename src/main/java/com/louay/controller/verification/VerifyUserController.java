package com.louay.controller.verification;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.authentication.UsersAuthentication;
import com.louay.model.entity.status.UserAccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.PushBuilder;
import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/user_verify")
public class VerifyUserController implements Serializable {
    private static final long serialVersionUID = 6981808810997826276L;
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

    @GetMapping("/verify_success")
    public String viewSuccessVerifyPage(PushBuilder pushBuilder){
        if (pushBuilder != null) {
            pushBuilder
                    .path("/static/images/background_geometry.png")
                    .path("/static/lib/bootstrap-4.5.1/css/bootstrap.min.css")
                    .path("/static/css/small_form.css")
                    .path("/static/css/background-large_form.css")
                    .path("/static/images/favicon.ico")
                    .path("/static/lib/popper-2.4.3/popper.min.js")
                    .path("/static/lib/bootstrap-4.5.1/js/bootstrap.min.js")
                    .path("/static/lib/jQuery-3.5.1/jquery-3.5.1.min.js")
                    .path("/WEB-INF/jsp/verify_done.jsp.jsp")
                    .push();
        }
        return "verify_done";
    }

    @GetMapping(value = "/perform_verify/{userId}/{verifyNumber}")
    public String verifyUserThenRedirectIt(@PathVariable String userId, @PathVariable String verifyNumber) {
        UsersAuthentication usersAuthentication = buildUsersAuthentication(userId);
        Integer verifyNumberInteger = Integer.parseInt(verifyNumber);
        if (verifyNumberInteger.equals(usersAuthentication.getVerificationNumber())) {
            updateValidUserAccountStatus(userId);
        }
        return "redirect:/user_verify/verify_success";
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
