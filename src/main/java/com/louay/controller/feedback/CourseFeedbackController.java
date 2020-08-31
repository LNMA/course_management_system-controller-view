package com.louay.controller.feedback;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.courses.Courses;
import com.louay.model.entity.feedback.CourseFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/course/{courseId}/feedback")
public class CourseFeedbackController implements Serializable {
    private static final long serialVersionUID = -8439626380078593471L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public CourseFeedbackController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        if (entitiesFactory == null || servicesFactory == null) {
            throw new IllegalArgumentException("factory cannot be null at CourseFeedbackController.class");
        }
        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @GetMapping
    public String viewCourseFeedbackPage() {
        return "/static/html/course_feedback.html";
    }

    @GetMapping(value = "/feedback_data")
    @ResponseBody
    public TreeSet<CourseFeedback> getFeedbackPostSet(
            @PathVariable(name = "courseId", required = false) String courseId) {

        long courseIdNumber = 0L;
        if (courseId != null) {
            courseIdNumber = Long.parseLong(courseId);
        }

        return findCourseFeedbackAndCommentToOneCourse(courseIdNumber);
    }

    private TreeSet<CourseFeedback> findCourseFeedbackAndCommentToOneCourse(Long courseId){
        CourseFeedback courseFeedback = buildCourseFeedback(courseId);
        Set<CourseFeedback> courseFeedbackSet = this.servicesFactory.getFeedbackService()
                .findCourseFeedbackAndCommentByCourseId(courseFeedback);

        TreeSet<CourseFeedback> feedbackTreeSet = new TreeSet<>();
        if (!courseFeedbackSet.isEmpty()){
            feedbackTreeSet.addAll(courseFeedbackSet);
        }

        return feedbackTreeSet;
    }

    private CourseFeedback buildCourseFeedback(Long courseId){
        CourseFeedback courseFeedback = this.entitiesFactory.getCourseFeedback();
        courseFeedback.setCourse(buildCourse(courseId));

        return courseFeedback;
    }

    private Courses buildCourse(Long courseId) {
        Courses courses = this.entitiesFactory.getCourses();
        courses.setInstructor(this.entitiesFactory.getInstructor());
        courses.setCourseID(courseId);

        return courses;
    }

}
