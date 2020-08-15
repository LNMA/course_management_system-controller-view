package com.louay.controller.signup;

import com.louay.model.entity.role.AccountsRoles;
import com.louay.model.entity.role.UsersRoles;
import com.louay.model.entity.status.UserAccountStatus;
import com.louay.model.entity.users.Admin;
import com.louay.model.entity.users.Users;
import com.louay.model.entity.users.picute.AccountPicture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentSignUpEntitiesFactory {
    private final Admin admin;
    private final Users users;
    private final AccountsRoles accountsRoles;
    private final UsersRoles usersRoles;
    private final AccountPicture accountPicture;
    private final UserAccountStatus userAccountStatus;

    @Autowired
    public StudentSignUpEntitiesFactory(Admin admin, Users users, AccountsRoles accountsRoles, UsersRoles usersRoles,
                                        AccountPicture accountPicture, UserAccountStatus userAccountStatus) {
        if (admin == null || users == null || accountsRoles == null || usersRoles == null || accountPicture == null
                || userAccountStatus == null) {
            throw new IllegalArgumentException("entities cannot be null at StudentSignUpEntitiesFactory.class!");
        }
        this.admin = admin;
        this.users = users;
        this.accountsRoles = accountsRoles;
        this.usersRoles = usersRoles;
        this.accountPicture = accountPicture;
        this.userAccountStatus = userAccountStatus;
    }

    Admin getAdmin() {
        return admin;
    }

    Users getUsers() {
        this.users.setAdmin(getAdmin());
        return users;
    }

    AccountsRoles getAccountsRoles() {
        return accountsRoles;
    }

    UsersRoles getUsersRoles() {
        this.usersRoles.setAccountsRoles(getAccountsRoles());
        this.usersRoles.setUsers(getAdmin());
        return usersRoles;
    }

    AccountPicture getAccountPicture() {
        this.accountPicture.setUsers(getUsers());
        return accountPicture;
    }

    UserAccountStatus getUserAccountStatus() {
        this.userAccountStatus.setUsers(getUsers());
        return userAccountStatus;
    }
}
