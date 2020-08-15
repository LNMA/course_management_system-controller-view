package com.louay.controller.signup;

import com.louay.model.service.account.AccountService;
import com.louay.model.service.role.RoleService;
import com.louay.model.service.status.StatusService;
import com.louay.model.service.userpic.AccountPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentSignUpServicesFactory {
    private final AccountService accountService;
    private final RoleService roleService;
    private final AccountPictureService pictureService;
    private final StatusService statusService;

    @Autowired
    public StudentSignUpServicesFactory(AccountService accountService, RoleService roleService,
                                        AccountPictureService pictureService, StatusService statusService) {
        if (accountService == null || roleService == null || pictureService == null || statusService == null){
            throw new IllegalArgumentException("Service cannot be null in StudentSignUpServiceWrapper.class!");
        }
        this.accountService = accountService;
        this.roleService = roleService;
        this.pictureService = pictureService;
        this.statusService = statusService;
    }

    AccountService getAccountService() {
        return accountService;
    }

    RoleService getRoleService() {
        return roleService;
    }

    AccountPictureService getPictureService() {
        return pictureService;
    }

    StatusService getStatusService() {
        return statusService;
    }
}
