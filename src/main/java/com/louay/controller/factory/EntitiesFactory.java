package com.louay.controller.factory;

import com.louay.model.entity.authentication.CookieLogin;
import com.louay.model.entity.authentication.UsersAuthentication;
import com.louay.model.entity.role.AccountsRoles;
import com.louay.model.entity.role.UsersRoles;
import com.louay.model.entity.status.UserAccountStatus;
import com.louay.model.entity.status.UserSignIn;
import com.louay.model.entity.users.Admin;
import com.louay.model.entity.users.Instructor;
import com.louay.model.entity.users.Student;
import com.louay.model.entity.users.Users;
import com.louay.model.entity.users.picute.AccountPicture;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EntitiesFactory {
    private Admin admin;
    private Users users;
    private Student student;
    private Instructor instructor;
    private AccountsRoles accountsRoles;
    private UsersRoles usersRoles;
    private AccountPicture accountPicture;
    private UserAccountStatus userAccountStatus;
    private UsersAuthentication usersAuthentication;
    private CookieLogin cookieLogin;
    private UserSignIn userSignIn;

    public Admin getAdmin() {
        setAdmin();
        return admin;
    }

    public void setAdmin() {
        this.admin = new Admin();
    }

    public Users getUsers() {
        setUsers();
        return users;
    }

    public void setUsers() {
        this.users = new Users();
    }

    public Student getStudent() {
        setStudent();
        return student;
    }

    public void setStudent() {
        this.student = new Student();
    }

    public Instructor getInstructor() {
        setInstructor();
        return instructor;
    }

    public void setInstructor() {
        this.instructor = new Instructor();
    }

    public AccountsRoles getAccountsRoles() {
        setAccountsRoles();
        return accountsRoles;
    }

    public void setAccountsRoles() {
        this.accountsRoles = new AccountsRoles();
    }

    public UsersRoles getUsersRoles() {
        setUsersRoles();
        return usersRoles;
    }

    public void setUsersRoles() {
        this.usersRoles = new UsersRoles();
    }

    public AccountPicture getAccountPicture() {
        setAccountPicture();
        return accountPicture;
    }

    public void setAccountPicture() {
        this.accountPicture = new AccountPicture();
    }

    public UserAccountStatus getUserAccountStatus() {
        setUserAccountStatus();
        return userAccountStatus;
    }

    public void setUserAccountStatus() {
        this.userAccountStatus = new UserAccountStatus();
    }

    public UsersAuthentication getUsersAuthentication() {
        setUsersAuthentication();
        return usersAuthentication;
    }

    public void setUsersAuthentication() {
        this.usersAuthentication = new UsersAuthentication();
    }

    public CookieLogin getCookieLogin() {
        setCookieLogin();
        return cookieLogin;
    }

    public void setCookieLogin() {
        this.cookieLogin = new CookieLogin();
    }

    public UserSignIn getUserSignIn() {
        setUserSignIn();
        return userSignIn;
    }

    public void setUserSignIn() {
        this.userSignIn = new UserSignIn();
    }
}
