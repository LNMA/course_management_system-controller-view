package com.louay.controller.student;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.controller.factory.WrappersFactory;
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

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/student/student_home/{email}")
public class StudentHomePageController implements Serializable {
    private static final long serialVersionUID = -6032312962777330181L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;
    private final WrappersFactory wrappersFactory;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public StudentHomePageController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory,
                                     WrappersFactory wrappersFactory, PasswordEncoder passwordEncoder) {
        if (entitiesFactory == null || servicesFactory == null || wrappersFactory == null || passwordEncoder == null) {
            throw new IllegalArgumentException("factory cannot be null at StudentHomePageController.class");
        }
        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
        this.wrappersFactory = wrappersFactory;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String viewStudentHome() {
        return "/static/html/student_home.html";
    }

    @PostMapping(value = "/profile_picture-update",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateStudentPicture(@RequestParam("file") MultipartFile file,
                                     @PathVariable(value = "email") String email){
        System.out.println(email);
        byte [] newImage = null;
        try{
            newImage = file.getBytes();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        String originalEmail = filterEmailUrlToOriginal(email);
        AccountPicture accountPicture = findAccountPicture(originalEmail);
        accountPicture.setPicture(newImage);

        this.servicesFactory.getPictureService().updateAccountPicture(accountPicture);
        return String.format("redirect:/student/student_home/%s", email);
    }

    @PostMapping(value = "/password-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStudentPassword(@RequestBody PasswordWrapper passwordWrapper,
                                                         @PathVariable(value = "email") String email) {
        if (!passwordWrapper.getNewPassword().equals(passwordWrapper.getReNewPassword())){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("new Password did not matched!");
        }

        String originalEmail = filterEmailUrlToOriginal(email);
        Admin admin = findAdmin(originalEmail);
        boolean isOldPasswordMatched =
                this.passwordEncoder.matches(passwordWrapper.getOldPassword(), admin.getPassword());

        if (!isOldPasswordMatched){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("old password is wrong!");
        }

        String encryptNewPassword = this.passwordEncoder.encode(passwordWrapper.getNewPassword());
        admin.setPassword(encryptNewPassword);
        this.servicesFactory.getAccountService().updateAccountByEmail(admin);
        return ResponseEntity.ok().body(encryptNewPassword);
    }

    private Admin findAdmin(String email){
        Admin admin = buildAdmin(email);
        return this.servicesFactory.getAccountService().findAccountByEmail(admin);
    }

    private Admin buildAdmin(String email){
        Admin admin = this.entitiesFactory.getAdmin();
        admin.setEmail(email);
        return admin;
    }

    @PostMapping(value = "/interests-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStudentInterests(@RequestBody Student student,
                                                        @PathVariable(value = "email") String email) {
        String originalEmail = filterEmailUrlToOriginal(email);
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
        String originalEmail = filterEmailUrlToOriginal(email);
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
        String originalEmail = filterEmailUrlToOriginal(email);
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
        String originalEmail = filterEmailUrlToOriginal(email);
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
        String originalEmail = filterEmailUrlToOriginal(email);
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
        String originalEmail = filterEmailUrlToOriginal(email);
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
        String originalEmail = filterEmailUrlToOriginal(email);
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
        String originalEmail = filterEmailUrlToOriginal(email);
        Student studentEntity = findStudent(originalEmail);
        studentEntity.setForename(student.getForename());
        studentEntity = this.servicesFactory.getAccountService()
                .updateStudentsDetailsByStudentID(studentEntity);
        return ResponseEntity.ok().body(studentEntity.getForename());
    }

    @GetMapping(value = "/myInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    StudentHomeWrapper getStudentHomeInfo(@PathVariable(value = "email") String email) {
        String originalEmail = filterEmailUrlToOriginal(email);

        return buildStudentHomeWrapper(originalEmail);
    }

    private StudentHomeWrapper buildStudentHomeWrapper(String originalEmail) {
        StudentHomeWrapper studentHomeWrapper = this.wrappersFactory.getStudentHomeWrapper();
        studentHomeWrapper.setStudent(findStudent(originalEmail));
        AccountPicture accountPicture = findAccountPicture(originalEmail);
        studentHomeWrapper.setPictureBase64(accountPicture.getBase64());

        return studentHomeWrapper;
    }

    private String filterEmailUrlToOriginal(String urlEmail) {
        int emailLength = urlEmail.length();
        String subEmail = urlEmail.substring(emailLength - 4, emailLength);
        if (subEmail.equals("-com")) {
            return urlEmail.substring(0, emailLength - 4) + ".com";
        }
        return urlEmail;
    }


    public Student findStudent(String email) {
        Student student = buildStudent(email);

        return this.servicesFactory.getAccountService().findStudentsDetailsByStudentID(student);
    }

    private Student buildStudent(String email) {
        Student student = this.entitiesFactory.getStudent();
        student.setAdmin(this.entitiesFactory.getAdmin());
        student.getAdmin().setEmail(email);
        student.setEmail(email);

        return student;
    }

    private AccountPicture findAccountPicture(String email) {
        return this.servicesFactory.getPictureService().findAccountPictureByUserId(buildAccountPicture(email));
    }

    private AccountPicture buildAccountPicture(String email) {
        AccountPicture accountPicture = this.entitiesFactory.getAccountPicture();
        accountPicture.setUsers(buildStudent(email));

        return accountPicture;
    }
}
