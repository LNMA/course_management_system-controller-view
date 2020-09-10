package com.louay.controller.review;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.role.AccountsRoles;
import com.louay.model.entity.role.UsersRoles;
import com.louay.model.entity.users.Instructor;
import com.louay.model.entity.users.Student;
import com.louay.model.entity.users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/review/account/{email:.+}")
public class ReviewAccountControllers implements Serializable {
    private static final long serialVersionUID = -8439626380078593471L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public ReviewAccountControllers(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @GetMapping
    public String viewReviewAccount() {
        return "/static/html/account_review-student.html";
    }

    @GetMapping(value = "/user_info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Users getUserInfo(@PathVariable(value = "email") String email) {
        AccountsRoles accountsRoles = findAccountsRoles(email);

        switch (accountsRoles.getRoleName()) {
            case STUDENT:
                return findStudent(email);
            case INSTRUCTOR:
                return findInstructor(email);
            default:
                throw new UnsupportedOperationException("student and instructor are only supported!.");
        }
    }

    private Instructor findInstructor(String email) {
        Instructor instructor = buildInstructor(email);

        return this.servicesFactory.getAccountService().findInstructorsDetailsByInstructorID(instructor);
    }

    private Student findStudent(String email) {
        Student student = buildStudent(email);

        return this.servicesFactory.getAccountService().findStudentsDetailsByStudentID(student);
    }

    private Instructor buildInstructor(String email){
        Instructor instructor = this.entitiesFactory.getInstructor();
        instructor.setEmail(email);

        return instructor;
    }

    private Student buildStudent(String email){
        Student student = this.entitiesFactory.getStudent();
        student.setEmail(email);

        return student;
    }

    private AccountsRoles findAccountsRoles(String email) {
        UsersRoles usersRoles = findUsersRoles(email);
        AccountsRoles accountsRoles = buildAccountsRoles(usersRoles.getAccountsRoles().getRoleID());

        return this.servicesFactory.getRoleService().findAccountRoleByRoleId(accountsRoles);
    }

    private UsersRoles findUsersRoles(String email) {
        UsersRoles usersRoles = buildUsersRoles(email);

        return this.servicesFactory.getRoleService().findUsersRolesByUserId(usersRoles);
    }

    private AccountsRoles buildAccountsRoles(Long roleId) {
        AccountsRoles accountsRoles = this.entitiesFactory.getAccountsRoles();
        accountsRoles.setRoleID(roleId);

        return accountsRoles;
    }

    private UsersRoles buildUsersRoles(String email) {
        UsersRoles usersRoles = this.entitiesFactory.getUsersRoles();
        Users users = buildUsers(email);
        usersRoles.setUsers(users.getAdmin());

        return usersRoles;
    }

    private Users buildUsers(String email) {
        Users users = this.entitiesFactory.getUsers();
        users.setEmail(email);
        users.setAdmin(this.entitiesFactory.getAdmin());
        users.getAdmin().setEmail(email);

        return users;
    }

}
