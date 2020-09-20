package com.louay.controller.member;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.courses.members.CourseMembers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/member")
public class CourseMemberController implements Serializable {
    private static final long serialVersionUID = 4584083592911898824L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public CourseMemberController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @PostMapping("/{email:.+}/{courseId}/be_a_member")
    public ResponseEntity<String> joinUserToCourse(@PathVariable(value = "email") String email,
                                                   @PathVariable(value = "courseId") String courseId) {
        Assert.notNull(email, "email cannot be null!.");
        Assert.notNull(courseId, "courseId cannot be null!.");

        Long courseIdNumber = Long.valueOf(courseId);
        System.out.println("yest");

        createCourseMembers(courseIdNumber, email);
        return ResponseEntity.status(HttpStatus.CREATED).body("Join Successfully.");
    }

    private void createCourseMembers(Long courseId, String email) {
        CourseMembers courseMembers = buildCourseMembers(courseId, email);

        this.servicesFactory.getCourseMemberService().createCourseMember(courseMembers);
    }

    private CourseMembers buildCourseMembers(Long courseId, String email) {
        CourseMembers courseMembers = this.entitiesFactory.getCourseMembers();
        courseMembers.setStudent(this.entitiesFactory.getStudent());
        courseMembers.getStudent().setEmail(email);
        courseMembers.setCourse(this.entitiesFactory.getCourses());
        courseMembers.getCourse().setCourseID(courseId);

        return courseMembers;
    }
}
