package com.louay.controller.review;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.courses.Courses;
import com.louay.model.entity.courses.members.CourseMembers;
import com.louay.model.entity.users.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/review/course/{myEmail:.+}/{courseId}")
public class ReviewCourseController implements Serializable {
    private static final long serialVersionUID = 172936968801324699L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public ReviewCourseController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @GetMapping
    public String viewReviewCourse() {
        return "/static/html/course_review.html";
    }

    @GetMapping(value = "/course_info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Courses getCourseInfo(@PathVariable(value = "courseId") String courseId) {
        Assert.notNull(courseId, "courseId cannot be null!.");

        Long courseIdNumber = Long.valueOf(courseId);

        return findCourses(courseIdNumber);
    }

    private Courses findCourses(Long courseId) {
        Courses courses = buildCourses(courseId);

        return this.servicesFactory.getCourseService().findCourseByCourseId(courses);
    }

    private Courses buildCourses(Long courseId) {
        Courses courses = this.entitiesFactory.getCourses();
        courses.setCourseID(courseId);

        return courses;
    }

    @GetMapping(value = "/is_student_join_to_this_course", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean isStudentJoinToThisCourse(@PathVariable(value = "courseId") String courseId,
                                             @PathVariable(value = "myEmail") String email) {
        Assert.notNull(courseId, "courseId cannot be null!.");
        Assert.notNull(email, "email cannot be null!.");

        Long courseIdNumber = Long.valueOf(courseId);

        return findIsStudentJoinToThisCourse(courseIdNumber, email);
    }

    @GetMapping(value = "/get_course_member", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<CourseMembers> getCourseMembers(@PathVariable(value = "courseId") String courseId) {
        Assert.notNull(courseId, "courseId cannot be null!.");

        Long courseIdNumber = Long.valueOf(courseId);

        return findCourseMembers(courseIdNumber);
    }

    private Set<CourseMembers> findCourseMembers(Long courseId) {
        CourseMembers courseMembers = buildCourseMembers(courseId);
        return this.servicesFactory.getCourseMemberService().findCourseMemberEagerStudentByCourseId(courseMembers);
    }

    private CourseMembers buildCourseMembers(Long courseId) {
        CourseMembers courseMembers = this.entitiesFactory.getCourseMembers();
        courseMembers.setCourse(buildCourses(courseId));

        return courseMembers;
    }

    private Boolean findIsStudentJoinToThisCourse(Long courseId, String email) {
        CourseMembers courseMembers = buildCourseMembers(courseId, email);

        return this.servicesFactory.getCourseMemberService().isStudentMemberAtThisCourse(courseMembers);
    }

    private CourseMembers buildCourseMembers(Long courseId, String email) {
        CourseMembers courseMembers = this.entitiesFactory.getCourseMembers();
        courseMembers.setCourse(buildCourses(courseId));
        courseMembers.setStudent(this.entitiesFactory.getStudent());
        courseMembers.getStudent().setEmail(email);

        return courseMembers;
    }

    @GetMapping(value = "/{instructorEmail:.+}/get_instructor_info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Instructor getInstructor(@PathVariable(value = "instructorEmail") String email){
        Assert.notNull(email, "email cannot be null!.");

        return findInstructor(email);
    }

    private Instructor findInstructor(String email){
        Instructor instructor = buildInstructor(email);

        return this.servicesFactory.getAccountService().findInstructorsDetailsByInstructorID(instructor);
    }

    private Instructor buildInstructor(String email){
        Instructor instructor = this.entitiesFactory.getInstructor();
        instructor.setEmail(email);

        return instructor;
    }

    @GetMapping(value = "/get_time", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Calendar getCurrentTime(){
        return Calendar.getInstance();
    }
}
