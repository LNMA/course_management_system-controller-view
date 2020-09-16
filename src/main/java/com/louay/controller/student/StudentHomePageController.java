package com.louay.controller.student;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.controller.factory.WrappersFactory;
import com.louay.model.entity.status.UserSignIn;
import com.louay.model.entity.users.Student;
import com.louay.model.entity.wrapper.StudentHomeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.PushBuilder;
import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/student/student_home/{email:.+}")
public class StudentHomePageController implements Serializable {
    private static final long serialVersionUID = -356187125743484272L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;
    private final WrappersFactory wrappersFactory;

    @Autowired
    public StudentHomePageController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory,
                                     WrappersFactory wrappersFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");
        Assert.notNull(wrappersFactory, "wrappersFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
        this.wrappersFactory = wrappersFactory;
    }

    @GetMapping
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

    @RequestMapping(value = "/interests-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PATCH)
    public ResponseEntity<String> updateStudentInterests(@RequestBody Student student,
                                                         @PathVariable(value = "email") String email) {
        Assert.notNull(email, "email cannot be null!.");
        Assert.notNull(student.getInterests(), "interests cannot be null!.");

        Student studentEntity = findStudent(email);
        studentEntity.setInterests(student.getInterests());
        studentEntity = this.servicesFactory.getAccountService()
                .updateStudentsDetailsByStudentID(studentEntity);
        return ResponseEntity.ok().body(studentEntity.getInterests());
    }

    @RequestMapping(value = "/headline-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PATCH)
    public ResponseEntity<String> updateStudentHeadline(@RequestBody Student student,
                                                        @PathVariable(value = "email") String email) {
        Assert.notNull(email, "email cannot be null!.");
        Assert.notNull(student.getHeadline(), "headline cannot be null!.");

        Student studentEntity = findStudent(email);
        studentEntity.setHeadline(student.getHeadline());
        studentEntity = this.servicesFactory.getAccountService()
                .updateStudentsDetailsByStudentID(studentEntity);
        return ResponseEntity.ok().body(studentEntity.getHeadline());
    }

    @GetMapping(value = "/myInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    StudentHomeWrapper getStudentHomeInfo(@PathVariable(value = "email") String email) {
        Assert.notNull(email, "email cannot be null!.");

        return buildStudentHomeWrapper(email);
    }

    private StudentHomeWrapper buildStudentHomeWrapper(String originalEmail) {
        StudentHomeWrapper studentHomeWrapper = this.wrappersFactory.getStudentHomeWrapper();
        studentHomeWrapper.setStudent(findStudent(originalEmail));
        if (isUserSignIn(originalEmail)) {
            studentHomeWrapper.setLastSignInDate(findUserLastSignIn(originalEmail).getSignInDate());
        }

        return studentHomeWrapper;
    }

    private UserSignIn findUserLastSignIn(String originalEmail) {
        return this.servicesFactory.getStatusService().findUserSignInByUserId(buildUserSignIn(originalEmail))
                .get(0);
    }

    private Boolean isUserSignIn(String originalEmail) {
        return this.servicesFactory.getStatusService().isUserSignInExist(buildUserSignIn(originalEmail));
    }

    private UserSignIn buildUserSignIn(String originalEmail) {
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
}
