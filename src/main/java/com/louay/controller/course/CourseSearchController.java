package com.louay.controller.course;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.courses.Courses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping("/course_search")
public class CourseSearchController implements Serializable {
    private static final long serialVersionUID = 968921641272911988L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public CourseSearchController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @GetMapping
    public String viewCourseSearch() {
        return "/static/html/course_search.html";
    }

    @GetMapping(value = "/{pageNumber}/get_courses")
    @ResponseBody
    public List<Courses> getCoursePagination(@PathVariable(value = "pageNumber") Integer pageNumber) {
        return this.servicesFactory.getCourseService().findAllCoursePagination(pageNumber, 9);
    }

    @GetMapping(value = "/get_courses_row", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Long getCourseNumberRecord() {
        return this.servicesFactory.getCourseService().getCoursesCountRow();
    }
}
