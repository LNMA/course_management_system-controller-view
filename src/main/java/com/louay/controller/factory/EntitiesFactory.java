package com.louay.controller.factory;

import com.louay.model.entity.authentication.CookieLogin;
import com.louay.model.entity.authentication.UsersAuthentication;
import com.louay.model.entity.role.AccountsRoles;
import com.louay.model.entity.role.UsersRoles;
import com.louay.model.entity.status.UserAccountStatus;
import com.louay.model.entity.status.UserSignIn;
import com.louay.model.entity.users.Admin;
import com.louay.model.entity.users.Users;
import com.louay.model.entity.users.picute.AccountPicture;
import com.louay.model.entity.wrapper.AdminRememberMeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EntitiesFactory {
    private final Admin admin;
    private final Users users;
    private final AccountsRoles accountsRoles;
    private final UsersRoles usersRoles;
    private final AccountPicture accountPicture;
    private final UserAccountStatus userAccountStatus;
    private final UsersAuthentication usersAuthentication;
    private final CookieLogin cookieLogin;
    private final AdminRememberMeWrapper adminRememberMeWrapper;
    private final UserSignIn userSignIn;

    @Autowired
    public EntitiesFactory(Admin admin, Users users, AccountsRoles accountsRoles, UsersRoles usersRoles,
                           AccountPicture accountPicture, UserAccountStatus userAccountStatus,
                           UsersAuthentication usersAuthentication, CookieLogin cookieLogin,
                           AdminRememberMeWrapper adminRememberMeWrapper, UserSignIn userSignIn) {
        if (admin == null || users == null || accountsRoles == null || usersRoles == null || accountPicture == null
                || userAccountStatus == null || usersAuthentication == null || cookieLogin == null ||
                adminRememberMeWrapper == null || userSignIn == null) {
            throw new IllegalArgumentException("entities cannot be null at StudentSignUpEntitiesFactory.class!");
        }
        this.admin = admin;
        this.users = users;
        this.accountsRoles = accountsRoles;
        this.usersRoles = usersRoles;
        this.accountPicture = accountPicture;
        this.userAccountStatus = userAccountStatus;
        this.usersAuthentication = usersAuthentication;
        this.cookieLogin = cookieLogin;
        this.adminRememberMeWrapper = adminRememberMeWrapper;
        this.userSignIn = userSignIn;
    }

    public Admin getAdmin() {
        return admin;
    }

    public Users getUsers() {
        return users;
    }

    public Users getBuiltUsers() {
        this.users.setAdmin(getAdmin());
        return users;
    }

    public AccountsRoles getAccountsRoles() {
        return accountsRoles;
    }

    public UsersRoles getUsersRoles() {
        return usersRoles;
    }

    public UsersRoles getBuiltUsersRoles() {
        this.usersRoles.setAccountsRoles(getAccountsRoles());
        this.usersRoles.setUsers(getAdmin());
        return usersRoles;
    }

    public AccountPicture getAccountPicture() {
        return accountPicture;
    }

    public AccountPicture getBuiltAccountPicture() {
        this.accountPicture.setUsers(getUsers());
        return accountPicture;
    }

    public UserAccountStatus getUserAccountStatus() {
        return userAccountStatus;
    }

    public UserAccountStatus getBuiltUserAccountStatus() {
        this.userAccountStatus.setUsers(getUsers());
        return userAccountStatus;
    }

    public UsersAuthentication getUsersAuthentication() {
        return usersAuthentication;
    }

    public UsersAuthentication getBuiltUsersAuthentication() {
        this.usersAuthentication.setUsers(getUsers());
        return usersAuthentication;
    }

    public CookieLogin getCookieLogin() {
        return cookieLogin;
    }

    public AdminRememberMeWrapper getAdminRememberMeWrapper() {
        return adminRememberMeWrapper;
    }

    public UserSignIn getUserSignIn() {
        return userSignIn;
    }
}
