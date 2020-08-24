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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
        return admin;
    }

    @Autowired
    public void setAdmin(Admin admin) {
        if (admin == null) {
            throw new IllegalArgumentException("admin entities cannot be null at EntitiesFactory.class!.");
        }
        this.admin = admin;
    }

    public Users getUsers() {
        return users;
    }

    @Autowired
    @Qualifier("users")
    public void setUsers(Users users) {
        if (users == null) {
            throw new IllegalArgumentException("users entities cannot be null at EntitiesFactory.class!.");
        }
        this.users = users;
    }

    public Student getStudent() {
        return student;
    }

    @Autowired
    @Qualifier("student")
    public void setStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("student entities cannot be null at EntitiesFactory.class!.");
        }
        this.student = student;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    @Autowired
    @Qualifier("instructor")
    public void setInstructor(Instructor instructor) {
        if (instructor == null) {
            throw new IllegalArgumentException("instructor entities cannot be null at EntitiesFactory.class!.");
        }
        this.instructor = instructor;
    }

    public AccountsRoles getAccountsRoles() {
        return accountsRoles;
    }

    @Autowired
    public void setAccountsRoles(AccountsRoles accountsRoles) {
        if (accountsRoles == null) {
            throw new IllegalArgumentException("accountsRoles entities cannot be null at EntitiesFactory.class!.");
        }
        this.accountsRoles = accountsRoles;
    }

    public UsersRoles getUsersRoles() {
        return usersRoles;
    }

    @Autowired
    public void setUsersRoles(UsersRoles usersRoles) {
        if (usersRoles == null) {
            throw new IllegalArgumentException("usersRoles entities cannot be null at EntitiesFactory.class!.");
        }
        this.usersRoles = usersRoles;
    }

    public AccountPicture getAccountPicture() {
        return accountPicture;
    }

    @Autowired
    public void setAccountPicture(AccountPicture accountPicture) {
        if (accountPicture == null) {
            throw new IllegalArgumentException("accountPicture entities cannot be null at EntitiesFactory.class!.");
        }
        this.accountPicture = accountPicture;
    }

    public UserAccountStatus getUserAccountStatus() {
        return userAccountStatus;
    }

    @Autowired
    public void setUserAccountStatus(UserAccountStatus userAccountStatus) {
        if (userAccountStatus == null) {
            throw new IllegalArgumentException("userAccountStatus entities cannot be null at EntitiesFactory.class!.");
        }
        this.userAccountStatus = userAccountStatus;
    }

    public UsersAuthentication getUsersAuthentication() {
        return usersAuthentication;
    }

    @Autowired
    public void setUsersAuthentication(UsersAuthentication usersAuthentication) {
        if (usersAuthentication == null) {
            throw new IllegalArgumentException("usersAuthentication entities cannot be null at EntitiesFactory.class!.");
        }
        this.usersAuthentication = usersAuthentication;
    }

    public CookieLogin getCookieLogin() {
        return cookieLogin;
    }

    @Autowired
    public void setCookieLogin(CookieLogin cookieLogin) {
        if (cookieLogin == null) {
            throw new IllegalArgumentException("cookieLogin entities cannot be null at EntitiesFactory.class!.");
        }
        this.cookieLogin = cookieLogin;
    }

    public UserSignIn getUserSignIn() {
        return userSignIn;
    }

    @Autowired
    public void setUserSignIn(UserSignIn userSignIn) {
        if (userSignIn == null) {
            throw new IllegalArgumentException("userSignIn entities cannot be null at EntitiesFactory.class!.");
        }
        this.userSignIn = userSignIn;
    }
}
