package com.louay.controller.signup;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.controller.verification.SendingVerificationEmail;
import com.louay.model.entity.authentication.UsersAuthentication;
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
import java.security.SecureRandom;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
public class StudentSignUpController implements Serializable {
    private static final long serialVersionUID = 6281539711407978497L;
    private final ServicesFactory servicesFactory;
    private final EntitiesFactory entitiesFactory;
    private final FileProcess fileProcess;
    private final SendingVerificationEmail sendingVerificationEmail;
    private static final String SUCCESS_MESSAGE = "You are successfully sign up";
    private static final String DUPLICATE_EMAIL_ERROR_MESSAGE = "This email is already Used!.";

    @Autowired
    public StudentSignUpController(ServicesFactory servicesFactory,
                                   EntitiesFactory entitiesFactory, FileProcess fileProcess,
                                   SendingVerificationEmail sendingVerificationEmail) {
        if (servicesFactory == null || entitiesFactory == null || fileProcess == null
                || sendingVerificationEmail == null) {
            throw new IllegalArgumentException("factory cannot be null at StudentSignUpController.class");
        }
        this.servicesFactory = servicesFactory;
        this.entitiesFactory = entitiesFactory;
        this.fileProcess = fileProcess;
        this.sendingVerificationEmail = sendingVerificationEmail;
    }

    @RequestMapping(value = "/subStudentSignUp", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> studentSignUp(@Validated @RequestBody Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bindingResult.toString());
        } else {
            if (this.servicesFactory.getAccountService().isExistUsers(student)) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).eTag("email duplicated")
                        .body(DUPLICATE_EMAIL_ERROR_MESSAGE);
            }
            createStudent(student);
            createUserAndAccountRole(student);
            createUserAccountStatus(student);
            createAccountPicture(student);
            UsersAuthentication usersAuthentication = createUserAuthentication(student);
            this.sendingVerificationEmail.sendMessage(usersAuthentication);

            return ResponseEntity.ok().eTag("student create").body(SUCCESS_MESSAGE);
        }
    }

    private void createStudent(Student student) {
        this.servicesFactory.getAccountService().createStudentsDetails(student);
    }

    private void createUserAndAccountRole(Student student) {
        UsersRoles usersRoles = buildUsersRoles(student);

        this.servicesFactory.getRoleService().createUsersRoles(usersRoles);
    }

    private UsersRoles buildUsersRoles(Student student){
        UsersRoles usersRoles = this.entitiesFactory.getUsersRoles();
        usersRoles.setAccountsRoles(this.entitiesFactory.getAccountsRoles());
        usersRoles.getAccountsRoles().setRoleName(Role.STUDENT);
        usersRoles.setUsers(student.getAdmin());

        return usersRoles;
    }

    private void createUserAccountStatus(Student student) {
        UserAccountStatus accountStatus = buildUserAccountStatus(student);

        this.servicesFactory.getStatusService().createUserAccountStatus(accountStatus);
    }

    private UserAccountStatus buildUserAccountStatus(Student student){
        UserAccountStatus accountStatus = this.entitiesFactory.getUserAccountStatus();
        accountStatus.setUsers(student);
        accountStatus.setOnline(false);
        accountStatus.setValid(false);

        return accountStatus;
    }

    private void createAccountPicture(Student student) {
        AccountPicture accountPicture = buildAccountPicture(student);

        this.servicesFactory.getPictureService().createAccountPicture(accountPicture);
    }

    private AccountPicture buildAccountPicture(Student student){
        AccountPicture accountPicture = this.entitiesFactory.getAccountPicture();
        accountPicture.setUsers(student);
        try {
            //TODO: change image path
            accountPicture.setPicture(this.fileProcess.readFile("C:\\Users\\Oday Amr\\Documents\\IdeaProjects\\" +
                    "course_management_system-controller-view\\src\\main\\webapp\\static\\images\\person_black.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return accountPicture;
    }

    private UsersAuthentication createUserAuthentication(Student student){
        UsersAuthentication usersAuthentication = buildUsersAuthentication(student);

        this.servicesFactory.getAuthenticationService().createUsersAuthentication(usersAuthentication);

        return usersAuthentication;
    }

    private UsersAuthentication buildUsersAuthentication(Student student){
        UsersAuthentication usersAuthentication = this.entitiesFactory.getUsersAuthentication();
        usersAuthentication.setUsers(student);

        SecureRandom secureRandom = new SecureRandom();
        int verifyNumber = secureRandom.nextInt(10000000);
        usersAuthentication.setVerificationNumber(verifyNumber);

        return usersAuthentication;
    }
}
