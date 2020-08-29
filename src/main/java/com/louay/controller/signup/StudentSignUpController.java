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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.PushBuilder;
import java.io.IOException;
import java.io.Serializable;
import java.security.SecureRandom;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
public class StudentSignUpController implements Serializable {
    private static final long serialVersionUID = 1666221637949636790L;
    private final ServicesFactory servicesFactory;
    private final EntitiesFactory entitiesFactory;
    private final FileProcess fileProcess;
    private final SendingVerificationEmail sendingVerificationEmail;
    private final PasswordEncoder passwordEncoder;
    private static final String SUCCESS_MESSAGE = ", Please check your email to verify your account then sign in";
    private static final String DUPLICATE_EMAIL_ERROR_MESSAGE = "This email is already Used!.";

    @Autowired
    public StudentSignUpController(ServicesFactory servicesFactory,
                                   EntitiesFactory entitiesFactory, FileProcess fileProcess,
                                   SendingVerificationEmail sendingVerificationEmail,
                                   PasswordEncoder passwordEncoder) {
        if (servicesFactory == null || entitiesFactory == null || fileProcess == null
                || sendingVerificationEmail == null || passwordEncoder == null) {
            throw new IllegalArgumentException("factory cannot be null at StudentSignUpController.class");
        }
        this.servicesFactory = servicesFactory;
        this.entitiesFactory = entitiesFactory;
        this.fileProcess = fileProcess;
        this.sendingVerificationEmail = sendingVerificationEmail;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/student_sign_up")
    public String viewStudentSignUp(PushBuilder pushBuilder) {
        if (pushBuilder != null) {
            pushBuilder
                    .path("/static/images/background_geometry.png")
                    .path("/static/lib/bootstrap-4.5.1/css/bootstrap.min.css")
                    .path("/static/css/background-large_form.css")
                    .path("/static/images/favicon.ico")
                    .path("/static/js/service/register_student_submit.js")
                    .path("/static/js/service/country_state.js")
                    .path("/static/js/control/student_sign_up-controller.js")
                    .path("/static/js/app.js")
                    .path("/static/lib/angularJS-1.8.0/angular-messages.min.js")
                    .path("/static/lib/angularJS-1.8.0/angular.min.js")
                    .path("/static/lib/popper-2.4.3/popper.min.js")
                    .path("/static/lib/bootstrap-4.5.1/js/bootstrap.min.js")
                    .path("/static/lib/jQuery-3.5.1/jquery-3.5.1.min.js")
                    .push();
        }
        return "/static/html/student_register.html";
    }

    @RequestMapping(value = "/submit_student_sign_up", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> studentSignUpProcess(@Validated @RequestBody Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is something error!.");
        }

        if (student.getEmail() == null || student.getAdmin() == null || student.getAdmin().getEmail() == null ||
                student.getAdmin().getPassword() == null || student.getEmail().length() < 7 ||
                student.getAdmin().getPassword().length() < 8 || student.getAdmin().getEmail().length() < 7 ||
                student.getForename() == null || student.getForename().length() < 2 || student.getSurname() == null ||
                student.getSurname().length() < 2) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("All field must fills!.");
        }

        if (this.servicesFactory.getAccountService().isExistUsers(student)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(StudentSignUpController.DUPLICATE_EMAIL_ERROR_MESSAGE);
        }

        encryptPassword(student);
        createStudent(student);
        createUserAndAccountRole(student);
        createUserAccountStatus(student);
        createAccountPicture(student);
        UsersAuthentication usersAuthentication = createUserAuthentication(student);
        String emailResult = this.sendingVerificationEmail.sendMessage(usersAuthentication);

        return ResponseEntity.status(HttpStatus.CREATED).body(emailResult + StudentSignUpController.SUCCESS_MESSAGE);

    }

    private void encryptPassword(Student student) {
        student.getAdmin().setPassword(this.passwordEncoder.encode(student.getAdmin().getPassword()));
    }

    private void createStudent(Student student) {
        this.servicesFactory.getAccountService().createStudentsDetails(student);
    }

    private void createUserAndAccountRole(Student student) {
        UsersRoles usersRoles = buildUsersRoles(student);

        this.servicesFactory.getRoleService().createUsersRoles(usersRoles);
    }

    private UsersRoles buildUsersRoles(Student student) {
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

    private UserAccountStatus buildUserAccountStatus(Student student) {
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

    private AccountPicture buildAccountPicture(Student student) {
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

    private UsersAuthentication createUserAuthentication(Student student) {
        UsersAuthentication usersAuthentication = buildUsersAuthentication(student);

        this.servicesFactory.getAuthenticationService().createUsersAuthentication(usersAuthentication);

        return usersAuthentication;
    }

    private UsersAuthentication buildUsersAuthentication(Student student) {
        UsersAuthentication usersAuthentication = this.entitiesFactory.getUsersAuthentication();
        usersAuthentication.setUsers(student);

        SecureRandom secureRandom = new SecureRandom();
        int verifyNumber = secureRandom.nextInt(10000000);
        usersAuthentication.setVerificationNumber(verifyNumber);

        return usersAuthentication;
    }
}
