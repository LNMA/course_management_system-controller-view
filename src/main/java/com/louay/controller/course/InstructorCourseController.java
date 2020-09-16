package com.louay.controller.course;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.courses.Courses;
import com.louay.model.entity.status.UserAtCourse;
import com.louay.model.entity.users.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.Set;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
public class InstructorCourseController implements Serializable {
    private static final long serialVersionUID = 6902917737159401577L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public InstructorCourseController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @GetMapping(value = "/instructor/{email:.+}/to_my_course/{courseId}")
    public String joinToCourseThenRedirect(@PathVariable(value = "email", required = false) String email,
                                           @PathVariable(value = "courseId", required = false) String courseId) {
        if (email == null || courseId == null) {
            return "redirect:/login";
        }

        createOrUpdateUserAtCourse(email);

        return String.format("redirect:/course/%s", courseId);
    }

    private void createOrUpdateUserAtCourse(String email) {
        if (isUserAtCourseExist(email)) {
            this.servicesFactory.getStatusService().updateUserAtCourse(buildUserAtCourse(email));
        } else {
            this.servicesFactory.getStatusService().createUserAtCourse(buildUserAtCourse(email));
        }
    }

    private Boolean isUserAtCourseExist(String email) {
        UserAtCourse userAtCourse = buildUserAtCourse(email);
        return this.servicesFactory.getStatusService().isUserAtCourseExist(userAtCourse);
    }

    private UserAtCourse buildUserAtCourse(String email) {
        UserAtCourse userAtCourse = this.entitiesFactory.getUserAtCourse();
        userAtCourse.setUsers(this.entitiesFactory.getUsers());
        userAtCourse.getUsers().setEmail(email);

        return userAtCourse;
    }

    @GetMapping(value = "/instructor/{email:.+}/my_course_info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<Courses> getInstructorCourses(@PathVariable(value = "email") String email) {
        Assert.notNull(email, "email cannot be null!.");

        return findInstructorTeachingCourses(email);
    }

    private Set<Courses> findInstructorTeachingCourses(String email) {
        Courses courses = buildCourse(email);

        return this.servicesFactory.getCourseService().findCourseByInstructorId(courses);
    }

    private Courses buildCourse(String email) {
        Instructor instructor = buildInstructor(email);

        Courses courses = this.entitiesFactory.getCourses();
        courses.setInstructor(instructor);

        return courses;
    }

    private Instructor buildInstructor(String email) {
        Instructor instructor = this.entitiesFactory.getInstructor();
        instructor.setEmail(email);
        instructor.setAdmin(this.entitiesFactory.getAdmin());
        instructor.getAdmin().setEmail(email);

        return instructor;
    }
}
