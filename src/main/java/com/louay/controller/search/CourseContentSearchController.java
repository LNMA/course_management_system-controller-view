package com.louay.controller.search;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.controller.factory.WrappersFactory;
import com.louay.model.entity.feedback.CourseFeedback;
import com.louay.model.entity.material.CourseMaterials;
import com.louay.model.entity.wrapper.CourseSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/search/{courseId}/{key}")
public class CourseContentSearchController implements Serializable {
    private static final long serialVersionUID = 249461374280036546L;
    private final ServicesFactory servicesFactory;
    private final EntitiesFactory entitiesFactory;
    private final WrappersFactory wrappersFactory;

    @Autowired
    public CourseContentSearchController(ServicesFactory servicesFactory, EntitiesFactory entitiesFactory,
                                         WrappersFactory wrappersFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");
        Assert.notNull(wrappersFactory, "wrappersFactory cannot be null!.");

        this.servicesFactory = servicesFactory;
        this.entitiesFactory = entitiesFactory;
        this.wrappersFactory = wrappersFactory;
    }

    @GetMapping
    public String viewCourseSearchContent() {
        return "/static/html/course_content_search.html";
    }

    @GetMapping(value = "/get_search_result", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<Object> getCourseSearchSet(@PathVariable(value = "courseId", required = false) String courseId,
                                          @PathVariable(value = "key", required = false) String key) {
        Assert.notNull(courseId, "courseId cannot be null!.");
        Assert.notNull(key, "key cannot be null!.");

        Long courseIdNumber = Long.valueOf(courseId);
        CourseSearch courseSearch = buildCourseSearch(key, courseIdNumber);

        return assembleSet(courseSearch);
    }

    private Set<Object> assembleSet(CourseSearch courseSearch) {
        Set<Object> objectSet = new HashSet<>();
        Set<CourseMaterials> courseMaterialsSet = findCourseMaterialsSet(courseSearch);
        Set<CourseFeedback> courseFeedbackSet = findCourseFeedbackSet(courseSearch);

        if (!courseFeedbackSet.isEmpty()) {
            objectSet.addAll(courseFeedbackSet);
        }

        if (!courseMaterialsSet.isEmpty()) {
            objectSet.addAll(courseMaterialsSet);
        }

        return objectSet;
    }

    private Set<CourseMaterials> findCourseMaterialsSet(CourseSearch courseSearch) {
        return this.servicesFactory.getMaterialService().findCourseMaterialsLikeToCourseSearch(courseSearch);
    }

    private Set<CourseFeedback> findCourseFeedbackSet(CourseSearch courseSearch) {
        return this.servicesFactory.getFeedbackService().findCourseFeedbackLikeToCourseSearch(courseSearch);
    }

    private CourseSearch buildCourseSearch(String key, Long courseId) {
        CourseSearch courseSearch = this.wrappersFactory.getCourseSearch();
        courseSearch.setCourses(this.entitiesFactory.getCourses());
        courseSearch.getCourses().setCourseID(courseId);
        courseSearch.setKey(key);

        return courseSearch;
    }
}
