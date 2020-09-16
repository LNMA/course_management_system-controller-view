package com.louay.controller.instructor;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.controller.factory.WrappersFactory;
import com.louay.model.entity.status.UserSignIn;
import com.louay.model.entity.users.Instructor;
import com.louay.model.entity.wrapper.InstructorHomeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/instructor/instructor_home/{email:.+}")
public class InstructorHomeController implements Serializable {
    private static final long serialVersionUID = 3466158131955579506L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;
    private final WrappersFactory wrappersFactory;

    @Autowired
    public InstructorHomeController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory,
                                    WrappersFactory wrappersFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");
        Assert.notNull(wrappersFactory, "wrappersFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
        this.wrappersFactory = wrappersFactory;
    }

    @GetMapping
    public String viewInstructorHomePage() {
        return "/static/html/instructor-home.html";
    }

    @RequestMapping(value = "/profile_visibility-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PATCH)
    public ResponseEntity<String> editInstructorProfileVisibility(@RequestBody Instructor instructor,
                                                                  @PathVariable(value = "email") String email) {
        Assert.notNull(email, "email cannot be null!.");
        Assert.notNull(instructor.getProfileVisibility(), "profileVisibility cannot be null!.");

        Instructor instructorEntity = findInstructor(email);
        instructorEntity.setProfileVisibility(instructor.getProfileVisibility());
        instructorEntity = updateInstructor(instructorEntity);

        return ResponseEntity.ok().body(instructorEntity.getProfileVisibility().getProfileVisibility());
    }

    @RequestMapping(value = "/portfolio-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PATCH)
    public ResponseEntity<String> editInstructorPortfolio(@RequestBody Instructor instructor,
                                                          @PathVariable(value = "email") String email) {
        Assert.notNull(email, "email cannot be null!.");
        Assert.notNull(instructor.getPortfolio(), "portfolio cannot be null!.");

        Instructor instructorEntity = findInstructor(email);
        instructorEntity.setPortfolio(instructor.getPortfolio());
        instructorEntity = updateInstructor(instructorEntity);

        return ResponseEntity.ok().body(instructorEntity.getPortfolio());
    }

    @RequestMapping(value = "/nickname-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PATCH)
    public ResponseEntity<String> editInstructorNickname(@RequestBody Instructor instructor,
                                                         @PathVariable(value = "email") String email) {
        Assert.notNull(email, "email cannot be null!.");
        Assert.notNull(instructor.getNickname(), "nickname cannot be null!.");

        Instructor instructorEntity = findInstructor(email);
        instructorEntity.setNickname(instructor.getNickname());
        instructorEntity = updateInstructor(instructorEntity);

        return ResponseEntity.ok().body(instructorEntity.getNickname());
    }

    @RequestMapping(value = "/specialty-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PATCH)
    public ResponseEntity<String> editInstructorSpecialty(@RequestBody Instructor instructor,
                                                          @PathVariable(value = "email") String email) {
        Assert.notNull(email, "email cannot be null!.");
        Assert.notNull(instructor.getSpecialty(), "speciality cannot be null!.");

        Instructor instructorEntity = findInstructor(email);
        instructorEntity.setSpecialty(instructor.getSpecialty());
        instructorEntity = updateInstructor(instructorEntity);

        return ResponseEntity.ok().body(instructorEntity.getSpecialty());
    }

    @RequestMapping(value = "/headline-update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PATCH)
    public ResponseEntity<String> editInstructorHeadline(@RequestBody Instructor instructor,
                                                         @PathVariable(value = "email") String email) {
        Assert.notNull(email, "email cannot be null!.");
        Assert.notNull(instructor.getHeadline(), "headline cannot be null!.");

        Instructor instructorEntity = findInstructor(email);
        instructorEntity.setHeadline(instructorEntity.getHeadline());
        instructorEntity = updateInstructor(instructor);

        return ResponseEntity.ok().body(instructorEntity.getHeadline());
    }

    private Instructor updateInstructor(Instructor instructor) {
        return this.servicesFactory.getAccountService().updateInstructorsDetailsByInstructorID(instructor);
    }

    @GetMapping(value = "/my_info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public InstructorHomeWrapper getInstructorInfo(
            @PathVariable(value = "email", required = false) String email) {
        Assert.notNull(email, "email cannot be null!.");

        return buildInstructorHomeWrapper(email);
    }

    private InstructorHomeWrapper buildInstructorHomeWrapper(String email) {
        InstructorHomeWrapper instructorHomeWrapper = this.wrappersFactory.getInstructorHomeWrapper();
        Instructor instructor = findInstructor(email);
        instructorHomeWrapper.setInstructor(instructor);

        if (isUserSignIn(email)) {
            UserSignIn userSignIn = findUserLastSignIn(email);
            instructorHomeWrapper.setLastSignInDate(userSignIn.getSignInDate());
        }

        return instructorHomeWrapper;
    }

    private UserSignIn findUserLastSignIn(String email) {
        return this.servicesFactory.getStatusService().findUserSignInByUserId(buildUserSignIn(email))
                .get(0);
    }

    private Boolean isUserSignIn(String email) {
        return this.servicesFactory.getStatusService().isUserSignInExist(buildUserSignIn(email));
    }

    private UserSignIn buildUserSignIn(String email) {
        UserSignIn userSignIn = this.entitiesFactory.getUserSignIn();
        userSignIn.setUsers(this.entitiesFactory.getUsers());
        userSignIn.getUsers().setEmail(email);

        return userSignIn;
    }

    private Instructor findInstructor(String email) {
        Instructor instructor = buildInstructor(email);

        return this.servicesFactory.getAccountService().findInstructorsDetailsByInstructorID(instructor);
    }

    private Instructor buildInstructor(String email) {
        Instructor instructor = this.entitiesFactory.getInstructor();
        instructor.setEmail(email);
        instructor.setAdmin(this.entitiesFactory.getAdmin());
        instructor.getAdmin().setEmail(email);

        return instructor;
    }
}
