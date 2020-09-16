package com.louay.controller.course;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.courses.Courses;
import com.louay.model.entity.courses.members.CourseMembers;
import com.louay.model.entity.courses.members.UsersAttendance;
import com.louay.model.entity.status.UserAtCourse;
import com.louay.model.entity.users.Student;
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
public class StudentCourseController implements Serializable {
    private static final long serialVersionUID = 1404921203140429569L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public StudentCourseController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @GetMapping(value = "/student/student_home/{email:.+}/to_my_course/{courseId}")
    public String joinToCourseThenRedirect(@PathVariable(value = "email", required = false) String email,
                                           @PathVariable(value = "courseId", required = false) String courseId) {
        if (email == null || courseId == null) {
            return "redirect:/login";
        }

        Long courseIdNumber = Long.valueOf(courseId);

        createUsersAttendance(email, courseIdNumber);
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
        return this.servicesFactory.getStatusService().isUserAtCourseExist(buildUserAtCourse(email));
    }

    private UserAtCourse buildUserAtCourse(String email) {
        UserAtCourse userAtCourse = this.entitiesFactory.getUserAtCourse();
        userAtCourse.setUsers(buildStudent(email));
        userAtCourse.setBusy(true);

        return userAtCourse;
    }

    private void createUsersAttendance(String email, Long courseId) {
        this.servicesFactory.getAttendanceService().createUsersAttendance(buildUsersAttendance(email, courseId));
    }

    private UsersAttendance buildUsersAttendance(String email, Long courseId) {
        UsersAttendance usersAttendance = this.entitiesFactory.getUsersAttendance();
        usersAttendance.setCourse(this.entitiesFactory.getCourses());
        usersAttendance.getCourse().setCourseID(courseId);
        usersAttendance.setStudent(buildStudent(email));

        return usersAttendance;
    }


    @GetMapping(value = "/student/student_home/{email:.+}/my_course", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Set<Courses> getStudentJoinCourse(@PathVariable(value = "email", required = false) String email) {
        Assert.notNull(email, "email cannot be null!.");

        if (isStudentJoinToAnyCourse(email)) {
            return findAllCourseInSetCourse(email);
        }

        return null;
    }

    private Set<Courses> findAllCourseInSetCourse(String email) {
        return (Set<Courses>) this.servicesFactory.getCourseService()
                .findAllCourseByCourseId(buildSetCourse(email));
    }

    private Set<Courses> buildSetCourse(String email) {
        Set<Courses> coursesSet = new HashSet<>();
        Student student = findStudentJoinCourses(email);

        for (CourseMembers cm : student.getCourseMembers()) {
            Courses courses = new Courses();
            courses.setCourseID(cm.getCourse().getCourseID());
            coursesSet.add(courses);
        }
        return coursesSet;
    }

    private Student findStudentJoinCourses(String email) {
        return this.servicesFactory.getAccountService().findStudentJoinCourseMemberByStudentId(buildStudent(email));
    }

    private Boolean isStudentJoinToAnyCourse(String email) {
        return this.servicesFactory.getCourseMemberService().isStudentMemberAtAnyCourse(buildCourseMembers(email));
    }

    private CourseMembers buildCourseMembers(String email) {
        CourseMembers courseMembers = this.entitiesFactory.getCourseMembers();
        courseMembers.setStudent(this.entitiesFactory.getStudent());
        courseMembers.getStudent().setEmail(email);

        return courseMembers;
    }

    private Student buildStudent(String email) {
        Student student = this.entitiesFactory.getStudent();
        student.setEmail(email);
        student.setAdmin(this.entitiesFactory.getAdmin());
        student.getAdmin().setEmail(email);

        return student;
    }
}
