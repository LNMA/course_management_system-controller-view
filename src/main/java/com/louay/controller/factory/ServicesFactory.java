package com.louay.controller.factory;

import com.louay.model.service.account.AccountService;
import com.louay.model.service.authentication.AuthenticationService;
import com.louay.model.service.role.RoleService;
import com.louay.model.service.status.StatusService;
import com.louay.model.service.userpic.AccountPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ServicesFactory {
    private final AccountService accountService;
    private final RoleService roleService;
    private final AccountPictureService pictureService;
    private final StatusService statusService;
    private final AuthenticationService authenticationService;

    @Autowired
    public ServicesFactory(AccountService accountService, RoleService roleService,
                           AccountPictureService pictureService, StatusService statusService,
                           AuthenticationService authenticationService) {
        if (accountService == null || roleService == null || pictureService == null || statusService == null ||
                authenticationService == null) {
            throw new IllegalArgumentException("Service cannot be null in StudentSignUpServiceWrapper.class!");
        }
        this.accountService = accountService;
        this.roleService = roleService;
        this.pictureService = pictureService;
        this.statusService = statusService;
        this.authenticationService = authenticationService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    public AccountPictureService getPictureService() {
        return pictureService;
    }

    public StatusService getStatusService() {
        return statusService;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }
}
