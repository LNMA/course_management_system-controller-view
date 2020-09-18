package com.louay.controller.review;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.courses.Courses;
import com.louay.model.entity.courses.members.CourseMembers;
import com.louay.model.entity.role.AccountsRoles;
import com.louay.model.entity.role.UsersRoles;
import com.louay.model.entity.status.UserAccountStatus;
import com.louay.model.entity.status.UserAtCourse;
import com.louay.model.entity.users.Instructor;
import com.louay.model.entity.users.Student;
import com.louay.model.entity.users.Users;
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
@RequestMapping(value = "/review/account/{myEmail:.+}/{emailVisit:.+}")
public class ReviewAccountControllers implements Serializable {
    private static final long serialVersionUID = -8439626380078593471L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public ReviewAccountControllers(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @GetMapping
    public String viewReviewAccount() {
        return "/static/html/account_review.html";
    }

    @GetMapping(value = "/user_info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Users getUserInfo(@PathVariable(value = "emailVisit") String email) {
        Assert.notNull(email, "email cannot be null");

        AccountsRoles accountsRoles = findAccountsRoles(email);

        switch (accountsRoles.getRoleName()) {
            case STUDENT:
                return findStudent(email);
            case INSTRUCTOR:
                return findInstructor(email);
            default:
                throw new UnsupportedOperationException("student and instructor are only supported!.");
        }
    }

    private Instructor findInstructor(String email) {
        Instructor instructor = buildInstructor(email);

        return this.servicesFactory.getAccountService().findInstructorsDetailsByInstructorID(instructor);
    }

    private Student findStudent(String email) {
        Student student = buildStudent(email);

        return this.servicesFactory.getAccountService().findStudentsDetailsByStudentID(student);
    }

    private Instructor buildInstructor(String email) {
        Instructor instructor = this.entitiesFactory.getInstructor();
        instructor.setEmail(email);

        return instructor;
    }

    private Student buildStudent(String email) {
        Student student = this.entitiesFactory.getStudent();
        student.setEmail(email);

        return student;
    }

    private AccountsRoles findAccountsRoles(String email) {
        UsersRoles usersRoles = findUsersRoles(email);
        AccountsRoles accountsRoles = buildAccountsRoles(usersRoles.getAccountsRoles().getRoleID());

        return this.servicesFactory.getRoleService().findAccountRoleByRoleId(accountsRoles);
    }

    private UsersRoles findUsersRoles(String email) {
        UsersRoles usersRoles = buildUsersRoles(email);

        return this.servicesFactory.getRoleService().findUsersRolesByUserId(usersRoles);
    }

    private AccountsRoles buildAccountsRoles(Long roleId) {
        AccountsRoles accountsRoles = this.entitiesFactory.getAccountsRoles();
        accountsRoles.setRoleID(roleId);

        return accountsRoles;
    }

    private UsersRoles buildUsersRoles(String email) {
        UsersRoles usersRoles = this.entitiesFactory.getUsersRoles();
        Users users = buildUsers(email);
        usersRoles.setUsers(users.getAdmin());

        return usersRoles;
    }

    private Users buildUsers(String email) {
        Users users = this.entitiesFactory.getUsers();
        users.setEmail(email);
        users.setAdmin(this.entitiesFactory.getAdmin());
        users.getAdmin().setEmail(email);

        return users;
    }

    @GetMapping(value = "/user_status", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean isAccountOnline(@PathVariable(value = "emailVisit") String email) {
        Assert.notNull(email, "email cannot be null");

        UserAccountStatus userAccountStatus = findUserAccountStatus(email);

        return userAccountStatus.getOnline();
    }

    private UserAccountStatus findUserAccountStatus(String email) {
        UserAccountStatus userAccountStatus = buildUserAccountStatus(email);

        return this.servicesFactory.getStatusService().findUserAccountStatusByUserId(userAccountStatus);
    }

    private UserAccountStatus buildUserAccountStatus(String email) {
        UserAccountStatus userAccountStatus = this.entitiesFactory.getUserAccountStatus();
        userAccountStatus.setUsers(this.entitiesFactory.getUsers());
        userAccountStatus.getUsers().setEmail(email);

        return userAccountStatus;
    }

    @GetMapping(value = "/user_at_course", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean isUserAtCourse(@PathVariable(value = "emailVisit") String email) {
        Assert.notNull(email, "email cannot be null");

        UserAtCourse userAtCourse = findUserAtCourse(email);

        return userAtCourse.getBusy();
    }

    private UserAtCourse findUserAtCourse(String email) {
        UserAtCourse userAtCourse = buildUserAtCourse(email);

        return this.servicesFactory.getStatusService().findUserAtCourseByUserId(userAtCourse);
    }

    private UserAtCourse buildUserAtCourse(String email) {
        UserAtCourse userAtCourse = this.entitiesFactory.getUserAtCourse();
        userAtCourse.setUsers(this.entitiesFactory.getUsers());
        userAtCourse.getUsers().setEmail(email);

        return userAtCourse;
    }

    @GetMapping(value = "/user_course_join", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<Courses> getStudentJoinCourse(@PathVariable(value = "emailVisit", required = false) String email) {
        Assert.notNull(email, "email cannot be null");

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

    @GetMapping(value = "/instructor_course_teach", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<Courses> getInstructorCourseTeach(@PathVariable(value = "emailVisit", required = false) String email) {
        Assert.notNull(email, "email cannot be null!.");

        return findCourseInstructorTeach(email);
    }

    private Set<Courses> findCourseInstructorTeach(String email){
        Courses courses = buildCourses(email);

        return this.servicesFactory.getCourseService().findCourseByInstructorId(courses);
    }

    private Courses buildCourses(String email){
        Courses courses = this.entitiesFactory.getCourses();
        courses.setInstructor(buildInstructor(email));

        return courses;
    }
}
