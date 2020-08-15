package com.louay.controller.signup;

import com.louay.model.entity.role.UsersRoles;
import com.louay.model.entity.status.UserAccountStatus;
import com.louay.model.entity.users.Student;
import com.louay.model.entity.users.constant.Role;
import com.louay.model.entity.users.picute.AccountPicture;
import com.louay.model.util.file.FileProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
public class StudentSignUpController implements Serializable {
    private static final long serialVersionUID = -7832727853818269416L;
    private final StudentSignUpServicesFactory servicesFactory;
    private final StudentSignUpEntitiesFactory entitiesFactory;
    private final FileProcess fileProcess;
    private static final String SUCCESS_MESSAGE = "You are successfully sign up";
    private static final String DUPLICATE_EMAIL_ERROR_MESSAGE = "This email is already Used!.";


    @Autowired
    public StudentSignUpController(StudentSignUpServicesFactory servicesFactory,
                                   StudentSignUpEntitiesFactory entitiesFactory, FileProcess fileProcess) {
        if (servicesFactory == null || entitiesFactory == null || fileProcess == null) {
            throw new IllegalArgumentException("factory cannot be null at StudentSignUpController.class");
        }
        this.servicesFactory = servicesFactory;
        this.entitiesFactory = entitiesFactory;
        this.fileProcess = fileProcess;
    }

    private StudentSignUpServicesFactory getServicesFactory() {
        return servicesFactory;
    }

    private StudentSignUpEntitiesFactory getEntitiesFactory() {
        return entitiesFactory;
    }

    public FileProcess getFileProcess() {
        return fileProcess;
    }

    @RequestMapping(value = "/subStudentSignUp", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> studentSignUp(@Validated @RequestBody Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bindingResult.toString());
        } else {
            if (getServicesFactory().getAccountService().isExistUsers(student)) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).eTag("email duplicated")
                        .body(DUPLICATE_EMAIL_ERROR_MESSAGE);
            }
            createStudent(student);
            createUserAndAccountRole(student);
            createUserAccountStatus(student);
            createAccountPicture(student);

            return ResponseEntity.ok().eTag("student create").body(SUCCESS_MESSAGE);
        }
    }

    private void createStudent(Student student) {
        getServicesFactory().getAccountService().createStudentsDetails(student);
    }

    private void createUserAndAccountRole(Student student) {
        UsersRoles usersRoles = getEntitiesFactory().getUsersRoles();
        usersRoles.getAccountsRoles().setRoleName(Role.STUDENT);
        usersRoles.getUsers().setEmail(student.getEmail());
        getServicesFactory().getRoleService().createUsersRoles(usersRoles);
    }

    private void createUserAccountStatus(Student student) {
        UserAccountStatus accountStatus = getEntitiesFactory().getUserAccountStatus();
        accountStatus.getUsers().setEmail(student.getEmail());
        accountStatus.setOnline(false);
        accountStatus.setValid(false);
        getServicesFactory().getStatusService().createUserAccountStatus(accountStatus);
    }

    private void createAccountPicture(Student student) {
        AccountPicture accountPicture = getEntitiesFactory().getAccountPicture();
        accountPicture.getUsers().setEmail(student.getEmail());
        try {
            //TODO: change image path
            accountPicture.setPicture(getFileProcess().readFile("C:\\Users\\Oday Amr\\Documents\\IdeaProjects\\" +
                    "course_management_system-controller-view\\src\\main\\webapp\\static\\images\\person_black.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        getServicesFactory().getPictureService().createAccountPicture(accountPicture);
    }
}
