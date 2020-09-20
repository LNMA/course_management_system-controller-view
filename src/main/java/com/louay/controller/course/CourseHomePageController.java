package com.louay.controller.course;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.controller.factory.WrappersFactory;
import com.louay.model.entity.courses.Courses;
import com.louay.model.entity.users.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/course/{courseId}")
public class CourseHomePageController implements Serializable {
    private static final long serialVersionUID = 3434744642746620434L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public CourseHomePageController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory,
                                    WrappersFactory wrappersFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");
        Assert.notNull(wrappersFactory, "wrappersFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @GetMapping
    public String viewCourseHomePage() {
        return "/static/html/course_home.html";
    }

    @GetMapping(value = "/course_instructor_info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Instructor getCourseInstructorInfo(@PathVariable(value = "courseId", required = false) String courseId) {
        Assert.notNull(courseId, "courseId cannot be null!.");

        long courseIdNumber = Long.parseLong(courseId);
        return buildInstructorWithProfilePicture(courseIdNumber);
    }

    private Instructor buildInstructorWithProfilePicture(Long courseId) {
        Courses courses = findCourse(courseId);
        Instructor instructor = findInstructor(courses.getInstructor().getEmail());
        instructor.setEmail(instructor.getEmail());

        return instructor;
    }

    private Instructor findInstructor(String email) {
        Instructor instructor = buildInstructor(email);

        return this.servicesFactory.getAccountService().findInstructorsDetailsByInstructorID(instructor);
    }

    private Instructor buildInstructor(String email) {
        Instructor instructor = this.entitiesFactory.getInstructor();
        instructor.setEmail(email);


        return instructor;
    }

    @GetMapping(value = "/course_info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Courses getCourseInfo(@PathVariable(value = "courseId", required = false) String courseId) {
        Assert.notNull(courseId, "courseId cannot be null!.");

        long courseIdNumber = Long.parseLong(courseId);
        return findCourse(courseIdNumber);
    }

    private Courses findCourse(Long courseId) {
        return this.servicesFactory.getCourseService().findCourseByCourseId(buildCourse(courseId));
    }

    private Courses buildCourse(Long courseId) {
        Courses courses = this.entitiesFactory.getCourses();
        courses.setCourseID(courseId);
        courses.setInstructor(this.entitiesFactory.getInstructor());

        return courses;
    }
}
