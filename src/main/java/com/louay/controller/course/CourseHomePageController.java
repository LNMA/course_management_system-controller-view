package com.louay.controller.course;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.controller.factory.WrappersFactory;
import com.louay.model.entity.courses.Courses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/course/{courseId}")
public class CourseHomePageController implements Serializable {
    private static final long serialVersionUID = -3084164645574862522L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;
    private final WrappersFactory wrappersFactory;

    @Autowired
    public CourseHomePageController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory,
                                    WrappersFactory wrappersFactory) {
        if (entitiesFactory == null || servicesFactory == null || wrappersFactory == null) {
            throw new IllegalArgumentException("factory cannot be null at CourseHomePageController.class");
        }
        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
        this.wrappersFactory = wrappersFactory;
    }

    @GetMapping
    public String viewCourseHomePage() {
        return "/static/html/course_home.html";
    }

    @GetMapping(value = "/course_info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Courses getCourseInfo(@PathVariable(value = "courseId", required = false) String courseId){
        if (courseId == null){
            return null;
        }

        long courseIdNumber = Long.parseLong(courseId);
        return findCourse(courseIdNumber);
    }

    private Courses findCourse(Long courseId){
        return this.servicesFactory.getCourseService().findCourseByCourseId(buildCourse(courseId));
    }

    private Courses buildCourse(Long courseId){
        Courses courses = this.entitiesFactory.getCourses();
        courses.setCourseID(courseId);
        courses.setInstructor(this.entitiesFactory.getInstructor());

        return courses;
    }
}
