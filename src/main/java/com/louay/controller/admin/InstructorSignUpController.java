package com.louay.controller.admin;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.controller.verification.SendingVerificationEmail;
import com.louay.model.entity.authentication.UsersAuthentication;
import com.louay.model.entity.role.UsersRoles;
import com.louay.model.entity.status.UserAccountStatus;
import com.louay.model.entity.status.UserAtCourse;
import com.louay.model.entity.users.Instructor;
import com.louay.model.entity.users.Student;
import com.louay.model.entity.users.constant.Role;
import com.louay.model.entity.users.picute.AccountPicture;
import com.louay.model.util.file.FileProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.security.SecureRandom;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/admin/register-instructor")
public class InstructorSignUpController implements Serializable {
    private static final long serialVersionUID = -3482104992145766184L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;
    private final FileProcess fileProcess;
    private final SendingVerificationEmail sendingVerificationEmail;
    private final PasswordEncoder passwordEncoder;
    private static final String SUCCESS_MESSAGE = ", Please notify the user to check his email.";
    private static final String DUPLICATE_EMAIL_ERROR_MESSAGE = "This email is already Used!.";

    @Autowired
    public InstructorSignUpController(ServicesFactory servicesFactory,
                                      EntitiesFactory entitiesFactory, FileProcess fileProcess,
                                      SendingVerificationEmail sendingVerificationEmail,
                                      PasswordEncoder passwordEncoder) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null!.");
        Assert.notNull(fileProcess, "fileProcess cannot be null!.");
        Assert.notNull(sendingVerificationEmail, "sendingVerificationEmail cannot be null!.");

        this.servicesFactory = servicesFactory;
        this.entitiesFactory = entitiesFactory;
        this.fileProcess = fileProcess;
        this.sendingVerificationEmail = sendingVerificationEmail;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String viewInstructorRegisterPage() {
        return "/static/html/instructor-register.html";
    }

    @PostMapping(value = "/submit_instructor_sign_up", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addInstructor(@Validated @RequestBody Instructor instructor,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is something error!.");
        }

        if (instructor.getEmail() == null || instructor.getAdmin() == null || instructor.getAdmin().getEmail() == null ||
                instructor.getAdmin().getPassword() == null || instructor.getEmail().length() < 7 ||
                instructor.getAdmin().getPassword().length() < 8 || instructor.getAdmin().getEmail().length() < 7 ||
                instructor.getForename() == null || instructor.getForename().length() < 2 || instructor.getSurname() == null ||
                instructor.getSurname().length() < 2 || instructor.getNickname() == null || instructor.getProfileVisibility() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("All field must fills!.");
        }

        if (this.servicesFactory.getAccountService().isExistUsers(instructor)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(InstructorSignUpController.DUPLICATE_EMAIL_ERROR_MESSAGE);
        }

        encryptPassword(instructor);
        createInstructor(instructor);
        createUserAndAccountRole(instructor);
        createUserAccountStatus(instructor);
        createUserAtCourse(instructor);
        createAccountPicture(instructor);
        UsersAuthentication usersAuthentication = createUserAuthentication(instructor);
        String emailResult = this.sendingVerificationEmail.sendMessage(usersAuthentication);

        return ResponseEntity.status(HttpStatus.CREATED).body(emailResult + InstructorSignUpController.SUCCESS_MESSAGE);
    }

    private void encryptPassword(Instructor instructor) {
        instructor.getAdmin().setPassword(this.passwordEncoder.encode(instructor.getAdmin().getPassword()));
    }

    private void createInstructor(Instructor instructor) {
        this.servicesFactory.getAccountService().createInstructorsDetails(instructor);
    }

    private void createUserAndAccountRole(Instructor instructor) {
        UsersRoles usersRoles = buildUsersRoles(instructor);

        this.servicesFactory.getRoleService().createUsersRoles(usersRoles);
    }

    private UsersRoles buildUsersRoles(Instructor instructor) {
        UsersRoles usersRoles = this.entitiesFactory.getUsersRoles();
        usersRoles.setAccountsRoles(this.entitiesFactory.getAccountsRoles());
        usersRoles.getAccountsRoles().setRoleName(Role.INSTRUCTOR);
        usersRoles.setUsers(instructor.getAdmin());

        return usersRoles;
    }

    private void createUserAtCourse(Instructor instructor) {
        UserAtCourse userAtCourse = buildUserAtCourse(instructor);

        this.servicesFactory.getStatusService().createUserAtCourse(userAtCourse);
    }

    private UserAtCourse buildUserAtCourse(Instructor instructor) {
        UserAtCourse userAtCourse = this.entitiesFactory.getUserAtCourse();
        userAtCourse.setUsers(instructor);
        userAtCourse.setBusy(false);

        return userAtCourse;
    }

    private void createUserAccountStatus(Instructor instructor) {
        UserAccountStatus accountStatus = buildUserAccountStatus(instructor);

        this.servicesFactory.getStatusService().createUserAccountStatus(accountStatus);
    }

    private UserAccountStatus buildUserAccountStatus(Instructor instructor) {
        UserAccountStatus accountStatus = this.entitiesFactory.getUserAccountStatus();
        accountStatus.setUsers(instructor);
        accountStatus.setOnline(false);
        accountStatus.setValid(false);

        return accountStatus;
    }

    private void createAccountPicture(Instructor instructor) {
        AccountPicture accountPicture = buildAccountPicture(instructor);

        this.servicesFactory.getPictureService().createAccountPicture(accountPicture);
    }

    private AccountPicture buildAccountPicture(Instructor instructor) {
        AccountPicture accountPicture = this.entitiesFactory.getAccountPicture();
        accountPicture.setUsers(instructor);
        try {
            FileUrlResource fileUrlResource = new FileUrlResource("src/main/webapp//static/images/person_black.png");
            accountPicture.setPicture(this.fileProcess.readFile(fileUrlResource.getFile().getAbsolutePath()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return accountPicture;
    }

    private UsersAuthentication createUserAuthentication(Instructor instructor) {
        UsersAuthentication usersAuthentication = buildUsersAuthentication(instructor);

        this.servicesFactory.getAuthenticationService().createUsersAuthentication(usersAuthentication);

        return usersAuthentication;
    }

    private UsersAuthentication buildUsersAuthentication(Instructor instructor) {
        UsersAuthentication usersAuthentication = this.entitiesFactory.getUsersAuthentication();
        usersAuthentication.setUsers(instructor);

        SecureRandom secureRandom = new SecureRandom();
        int verifyNumber = secureRandom.nextInt(10000000);
        usersAuthentication.setVerificationNumber(verifyNumber);

        return usersAuthentication;
    }

}
