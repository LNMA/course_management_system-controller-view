package com.louay.controller.student;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.controller.factory.WrappersFactory;
import com.louay.controller.util.filter.EmailFilter;
import com.louay.model.entity.status.UserSignIn;
import com.louay.model.entity.users.Admin;
import com.louay.model.entity.users.Student;
import com.louay.model.entity.users.picute.AccountPicture;
import com.louay.model.entity.wrapper.PasswordWrapper;
import com.louay.model.entity.wrapper.StudentHomeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.PushBuilder;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/student/student_home/{email}")
public class StudentHomePageController implements Serializable {
    private static final long serialVersionUID = -5181010821357568897L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;
    private final WrappersFactory wrappersFactory;
    private final PasswordEncoder passwordEncoder;
    private final EmailFilter emailFilter;

    @Autowired
    public StudentHomePageController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory,
                                     WrappersFactory wrappersFactory, PasswordEncoder passwordEncoder,
                                     EmailFilter emailFilter) {
        if (entitiesFactory == null || servicesFactory == null || wrappersFactory == null || passwordEncoder == null
                || emailFilter == null) {
            throw new IllegalArgumentException("factory cannot be null at StudentHomePageController.class");
        }
        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
        this.wrappersFactory = wrappersFactory;
        this.passwordEncoder = passwordEncoder;
        this.emailFilter = emailFilter;
    }

    @GetMapping(produces = {MediaType.TEXT_HTML_VALUE})
    public String viewStudentHome(PushBuilder pushBuilder) {
        if (pushBuilder != null) {
            pushBuilder
                    .path("/static/lib/bootstrap-4.5.1/css/bootstrap.min.css")
                    .path("/static/images/favicon.ico")
                    .path("/static/lib/angularJS-1.8.0/angular-sanitize.min.js")
                    .path("/static/lib/angularJS-1.8.0/angular-messages.min.js")
                    .path("/static/lib/angularJS-1.8.0/angular.min.js")
                    .path("/static/lib/popper-2.4.3/popper.min.js")
                    .path("/static/lib/bootstrap-4.5.1/js/bootstrap.min.js")
                    .path("/static/lib/jQuery-3.5.1/jquery-3.5.1.min.js")
                    .push();
        }
        return "/static/html/student_home.html";
    }

    @PostMapping(value = "/profile_picture-update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateStudentPicture(@RequestParam("file") MultipartFile file,
                                       @PathVariable(value = "email") String email) {
        byte[] newImage = null;
        try {
            newImage = file.getBytes();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        String originalEmail = this.emailFilter.filterEmailUrlToOriginal(email);
        AccountPicture accountPicture = findAccountPicture(originalEmail);
        accountPicture.setPicture(newImage);

        this.servicesFactory.getPictureService().updateAccountPicture(accountPicture);
        return String.format("redirect:/student/student_home/%s", email);
    }

    @PostMapping(value = "/password-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStudentPassword(@RequestBody PasswordWrapper passwordWrapper,
                                                        @PathVariable(value = "email") String email) {
        if (!passwordWrapper.getNewPassword().equals(passwordWrapper.getReNewPassword())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("new Password did not matched!");
        }

        String originalEmail = this.emailFilter.filterEmailUrlToOriginal(email);
        Admin admin = findAdmin(originalEmail);
        boolean isOldPasswordMatched =
                this.passwordEncoder.matches(passwordWrapper.getOldPassword(), admin.getPassword());

        if (!isOldPasswordMatched) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("old password is wrong!");
        }

        String encryptNewPassword = this.passwordEncoder.encode(passwordWrapper.getNewPassword());
        admin.setPassword(encryptNewPassword);
        this.servicesFactory.getAccountService().updateAccountByEmail(admin);
        return ResponseEntity.ok().body(encryptNewPassword);
    }

    private Admin findAdmin(String email) {
        Admin admin = buildAdmin(email);
        return this.servicesFactory.getAccountService().findAccountByEmail(admin);
    }

    private Admin buildAdmin(String email) {
        Admin admin = this.entitiesFactory.getAdmin();
        admin.setEmail(email);
        return admin;
    }

    @PostMapping(value = "/interests-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStudentInterests(@RequestBody Student student,
                                                         @PathVariable(value = "email") String email) {
        String originalEmail = this.emailFilter.filterEmailUrlToOriginal(email);
        Student studentEntity = findStudent(originalEmail);
        studentEntity.setInterests(student.getInterests());
        studentEntity = this.servicesFactory.getAccountService()
                .updateStudentsDetailsByStudentID(studentEntity);
        return ResponseEntity.ok().body(studentEntity.getInterests());
    }

    @PostMapping(value = "/headline-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStudentHeadline(@RequestBody Student student,
                                                        @PathVariable(value = "email") String email) {
        String originalEmail = this.emailFilter.filterEmailUrlToOriginal(email);
        Student studentEntity = findStudent(originalEmail);
        studentEntity.setHeadline(student.getHeadline());
        studentEntity = this.servicesFactory.getAccountService()
                .updateStudentsDetailsByStudentID(studentEntity);
        return ResponseEntity.ok().body(studentEntity.getHeadline());
    }

    @PostMapping(value = "/full_address-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> updateStudentFullAddress(@RequestBody Student student,
                                                                        @PathVariable(value = "email") String email) {
        String originalEmail = this.emailFilter.filterEmailUrlToOriginal(email);
        Student studentEntity = findStudent(originalEmail);
        studentEntity.setCountry(student.getCountry());
        studentEntity.setState(student.getState());
        studentEntity.setAddress(student.getAddress());
        studentEntity = this.servicesFactory.getAccountService()
                .updateStudentsDetailsByStudentID(studentEntity);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("country", studentEntity.getCountry());
        responseBody.put("state", studentEntity.getState());
        responseBody.put("address", studentEntity.getAddress());
        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping(value = "/phone-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStudentPhone(@RequestBody Student student,
                                                     @PathVariable(value = "email") String email) {
        String originalEmail = this.emailFilter.filterEmailUrlToOriginal(email);
        Student studentEntity = findStudent(originalEmail);
        studentEntity.setPhone(student.getPhone());
        studentEntity = this.servicesFactory.getAccountService()
                .updateStudentsDetailsByStudentID(studentEntity);
        return ResponseEntity.ok().body(studentEntity.getPhone());
    }

    @PostMapping(value = "/birthday-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStudentBirthday(@RequestBody Student student,
                                                        @PathVariable(value = "email") String email) {
        String originalEmail = this.emailFilter.filterEmailUrlToOriginal(email);
        Student studentEntity = findStudent(originalEmail);
        studentEntity.setBirthday(student.getBirthday());
        studentEntity = this.servicesFactory.getAccountService()
                .updateStudentsDetailsByStudentID(studentEntity);
        return ResponseEntity.ok().body(studentEntity.getBirthday().getTime().toString());
    }

    @PostMapping(value = "/gender-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStudentGender(@RequestBody Student student,
                                                      @PathVariable(value = "email") String email) {
        String originalEmail = this.emailFilter.filterEmailUrlToOriginal(email);
        Student studentEntity = findStudent(originalEmail);
        studentEntity.setGender(student.getGender());
        studentEntity = this.servicesFactory.getAccountService()
                .updateStudentsDetailsByStudentID(studentEntity);
        return ResponseEntity.ok().body(studentEntity.getGender().getGender());
    }

    @PostMapping(value = "/surname-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStudentSurname(@RequestBody Student student,
                                                       @PathVariable(value = "email") String email) {
        String originalEmail = this.emailFilter.filterEmailUrlToOriginal(email);
        Student studentEntity = findStudent(originalEmail);
        studentEntity.setSurname(student.getSurname());
        studentEntity = this.servicesFactory.getAccountService()
                .updateStudentsDetailsByStudentID(studentEntity);
        return ResponseEntity.ok().body(studentEntity.getSurname());
    }

    @PostMapping(value = "/forename-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStudentForename(@RequestBody Student student,
                                                        @PathVariable(value = "email") String email) {
        String originalEmail = this.emailFilter.filterEmailUrlToOriginal(email);
        Student studentEntity = findStudent(originalEmail);
        studentEntity.setForename(student.getForename());
        studentEntity = this.servicesFactory.getAccountService()
                .updateStudentsDetailsByStudentID(studentEntity);
        return ResponseEntity.ok().body(studentEntity.getForename());
    }

    @GetMapping(value = "/myInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    StudentHomeWrapper getStudentHomeInfo(@PathVariable(value = "email") String email) {
        String originalEmail = this.emailFilter.filterEmailUrlToOriginal(email);

        return buildStudentHomeWrapper(originalEmail);
    }

    private StudentHomeWrapper buildStudentHomeWrapper(String originalEmail) {
        StudentHomeWrapper studentHomeWrapper = this.wrappersFactory.getStudentHomeWrapper();
        studentHomeWrapper.setStudent(findStudent(originalEmail));
        if (isUserSignIn(originalEmail)){
            studentHomeWrapper.setLastSignInDate(findUserLastSignIn(originalEmail).getSignInDate());
        }

        return studentHomeWrapper;
    }

    private UserSignIn findUserLastSignIn(String originalEmail){
        return this.servicesFactory.getStatusService().findUserSignInByUserId(buildUserSignIn(originalEmail))
                .get(0);
    }

    private Boolean isUserSignIn(String originalEmail){
        return this.servicesFactory.getStatusService().isUserSignInExist(buildUserSignIn(originalEmail));
    }

    private UserSignIn buildUserSignIn(String originalEmail){
        UserSignIn userSignIn = this.entitiesFactory.getUserSignIn();
        userSignIn.setUsers(this.entitiesFactory.getUsers());
        userSignIn.getUsers().setEmail(originalEmail);

        return userSignIn;
    }

    private Student findStudent(String originalEmail) {
        Student student = buildStudent(originalEmail);

        return this.servicesFactory.getAccountService().findStudentsDetailsByStudentID(student);
    }

    private Student buildStudent(String originalEmail) {
        Student student = this.entitiesFactory.getStudent();
        student.setAdmin(this.entitiesFactory.getAdmin());
        student.getAdmin().setEmail(originalEmail);
        student.setEmail(originalEmail);

        return student;
    }

    private AccountPicture findAccountPicture(String originalEmail) {
        return this.servicesFactory.getPictureService().findAccountPictureByUserId(buildAccountPicture(originalEmail));
    }

    private AccountPicture buildAccountPicture(String originalEmail) {
        AccountPicture accountPicture = this.entitiesFactory.getAccountPicture();
        accountPicture.setUsers(buildStudent(originalEmail));

        return accountPicture;
    }
}
