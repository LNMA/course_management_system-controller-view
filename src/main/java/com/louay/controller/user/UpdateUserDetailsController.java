package com.louay.controller.user;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.users.Admin;
import com.louay.model.entity.users.Student;
import com.louay.model.entity.users.Users;
import com.louay.model.entity.users.picute.AccountPicture;
import com.louay.model.entity.wrapper.PasswordWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/user/update/{email:.+}")
public class UpdateUserDetailsController implements Serializable {
    private static final long serialVersionUID = 6906211650293007332L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UpdateUserDetailsController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory,
                                       PasswordEncoder passwordEncoder) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/profile_picture-update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateStudentPicture(@RequestParam("file") MultipartFile file,
                                       @PathVariable(value = "email") String email) {
        Assert.notNull(email, "email cannot be null!.");
        if (file.isEmpty() || file.getSize() > (1024 * 1024 * 4) ||
                !Objects.requireNonNull(file.getContentType()).contains("image")) {
            throw new IllegalStateException("It's seem there something wrong in while process this operation !.");
        }

        byte[] newImage = null;
        try {
            newImage = file.getBytes();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        AccountPicture accountPicture = findAccountPicture(email);
        accountPicture.setPicture(newImage);
        updateAccountPicture(accountPicture);

        return String.format("redirect:/student/student_home/%s", email);
    }

    private void updateAccountPicture(AccountPicture accountPicture) {
        this.servicesFactory.getPictureService().updateAccountPicture(accountPicture);
    }

    private AccountPicture findAccountPicture(String email) {
        AccountPicture accountPicture = buildAccountPicture(email);

        return this.servicesFactory.getPictureService().findAccountPictureByUserId(accountPicture);
    }

    private AccountPicture buildAccountPicture(String email) {
        AccountPicture accountPicture = this.entitiesFactory.getAccountPicture();
        accountPicture.setUsers(buildUser(email));

        return accountPicture;
    }

    @RequestMapping(value = "/password-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PATCH)
    public ResponseEntity<String> editStudentPassword(@RequestBody PasswordWrapper passwordWrapper,
                                                      @PathVariable(value = "email") String email) {
        Assert.notNull(email, "email cannot be null!.");
        Assert.notNull(passwordWrapper.getNewPassword(), "NewPassword cannot be null!.");
        Assert.notNull(passwordWrapper.getOldPassword(), "OldPassword cannot be null!.");

        if (!passwordWrapper.getNewPassword().equals(passwordWrapper.getReNewPassword())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("new Password did not matched!");
        }

        Admin admin = findAdmin(email);
        boolean isOldPasswordMatched =
                this.passwordEncoder.matches(passwordWrapper.getOldPassword(), admin.getPassword());

        if (!isOldPasswordMatched) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("old password is wrong!");
        }

        String encryptNewPassword = this.passwordEncoder.encode(passwordWrapper.getNewPassword());
        admin.setPassword(encryptNewPassword);
        admin = updateAdmin(admin);
        return ResponseEntity.ok().body(admin.getPassword());
    }

    private Admin updateAdmin(Admin admin) {
        return this.servicesFactory.getAccountService().updateAccountByEmail(admin);

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

    @RequestMapping(value = "/full_address-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PATCH)
    public ResponseEntity<Map<String, String>> editStudentFullAddress(@RequestBody Student student,
                                                                      @PathVariable(value = "email") String email) {
        Assert.notNull(email, "email cannot be null!.");
        Assert.notNull(student.getCountry(), "country cannot be null!.");
        Assert.notNull(student.getState(), "state cannot be null!.");
        Assert.notNull(student.getAddress(), "address cannot be null!.");

        Users userEntity = findUser(email);
        userEntity.setCountry(student.getCountry());
        userEntity.setState(student.getState());
        userEntity.setAddress(student.getAddress());
        userEntity = updateUser(userEntity);


        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("country", userEntity.getCountry());
        responseBody.put("state", userEntity.getState());
        responseBody.put("address", userEntity.getAddress());
        return ResponseEntity.ok().body(responseBody);
    }

    @RequestMapping(value = "/phone-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PATCH)
    public ResponseEntity<String> editStudentPhone(@RequestBody Student student,
                                                   @PathVariable(value = "email") String email) {
        Assert.notNull(email, "email cannot be null!.");
        Assert.notNull(student.getPhone(), "phone cannot be null!.");

        Users userEntity = findUser(email);
        userEntity.setPhone(student.getPhone());
        userEntity = updateUser(userEntity);

        return ResponseEntity.ok().body(userEntity.getPhone());
    }

    @RequestMapping(value = "/birthday-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PATCH)
    public ResponseEntity<String> editStudentBirthday(@RequestBody Student student,
                                                      @PathVariable(value = "email") String email) {
        Assert.notNull(email, "email cannot be null!.");
        Assert.notNull(student.getBirthday(), "birthday cannot be null!.");

        Users userEntity = findUser(email);
        userEntity.setBirthday(student.getBirthday());
        userEntity = updateUser(userEntity);

        return ResponseEntity.ok().body(userEntity.getBirthday().getTime().toString());
    }

    @RequestMapping(value = "/gender-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PATCH)
    public ResponseEntity<String> editStudentGender(@RequestBody Student student,
                                                    @PathVariable(value = "email") String email) {
        Assert.notNull(email, "email cannot be null!.");
        Assert.notNull(student.getGender(), "gender cannot be null!.");

        Users userEntity = findUser(email);
        userEntity.setGender(student.getGender());
        userEntity = updateUser(userEntity);

        return ResponseEntity.ok().body(userEntity.getGender().getGender());
    }

    @RequestMapping(value = "/surname-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PATCH)
    public ResponseEntity<String> editStudentSurname(@RequestBody Student student,
                                                     @PathVariable(value = "email") String email) {
        Assert.notNull(email, "email cannot be null!.");
        Assert.notNull(student.getSurname(), "surname cannot be null!.");

        Users userEntity = findUser(email);
        userEntity.setSurname(student.getSurname());
        userEntity = updateUser(userEntity);

        return ResponseEntity.ok().body(userEntity.getSurname());
    }

    @RequestMapping(value = "/forename-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PATCH)
    public ResponseEntity<String> editUserForename(@RequestBody Student student,
                                                   @PathVariable(value = "email") String email) {
        Assert.notNull(email, "email cannot be null!.");
        Assert.notNull(student.getForename(), "forename cannot be null!.");

        Users userEntity = findUser(email);
        userEntity.setForename(student.getForename());
        userEntity = updateUser(userEntity);

        return ResponseEntity.ok().body(userEntity.getForename());
    }

    private Users updateUser(Users users) {
        return this.servicesFactory.getAccountService().updateUsersByUserID(users);
    }

    private Users findUser(String email) {
        Users users = buildUser(email);

        return this.servicesFactory.getAccountService().findUsersByUserID(users);
    }

    private Users buildUser(String email) {
        Users users = this.entitiesFactory.getUsers();
        users.setEmail(email);

        return users;
    }
}
