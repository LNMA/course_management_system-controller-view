package com.louay.controller.factory;

import com.louay.model.entity.authentication.CookieLogin;
import com.louay.model.entity.authentication.UsersAuthentication;
import com.louay.model.entity.courses.Courses;
import com.louay.model.entity.courses.members.CourseMembers;
import com.louay.model.entity.courses.members.UsersAttendance;
import com.louay.model.entity.feedback.CourseFeedback;
import com.louay.model.entity.feedback.FileFeedback;
import com.louay.model.entity.feedback.FileMessageFeedback;
import com.louay.model.entity.feedback.MessageFeedback;
import com.louay.model.entity.feedback.comment.Comment;
import com.louay.model.entity.material.FileMaterials;
import com.louay.model.entity.material.TextMaterials;
import com.louay.model.entity.role.AccountsRoles;
import com.louay.model.entity.role.UsersRoles;
import com.louay.model.entity.status.UserAccountStatus;
import com.louay.model.entity.status.UserAtCourse;
import com.louay.model.entity.status.UserSignIn;
import com.louay.model.entity.users.Admin;
import com.louay.model.entity.users.Instructor;
import com.louay.model.entity.users.Student;
import com.louay.model.entity.users.Users;
import com.louay.model.entity.users.picute.AccountPicture;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EntitiesFactory {

    public Admin getAdmin() {
        return new Admin();
    }

    public Users getUsers() {
        return new Users();
    }

    public Student getStudent() {
        return new Student();
    }

    public Instructor getInstructor() {
        return new Instructor();
    }

    public AccountsRoles getAccountsRoles() {
        return new AccountsRoles();
    }

    public UsersRoles getUsersRoles() {
        return new UsersRoles();
    }

    public AccountPicture getAccountPicture() {
        return new AccountPicture();
    }

    public UserAccountStatus getUserAccountStatus() {
        return new UserAccountStatus();
    }

    public UsersAuthentication getUsersAuthentication() {
        return new UsersAuthentication();
    }

    public CookieLogin getCookieLogin() {
        return new CookieLogin();
    }

    public UserSignIn getUserSignIn() {
        return new UserSignIn();
    }

    public UsersAttendance getUsersAttendance() {
        return new UsersAttendance();
    }

    public UserAtCourse getUserAtCourse() {
        return new UserAtCourse();
    }

    public CourseMembers getCourseMembers() {
        return new CourseMembers();
    }

    public Courses getCourses() {
        return new Courses();
    }

    public FileMaterials getFileMaterials() {
        return new FileMaterials();
    }

    public TextMaterials getTextMaterials() {
        return new TextMaterials();
    }

    public CourseFeedback getCourseFeedback() {
        return new CourseFeedback();
    }

    public FileFeedback getFileFeedback() {
        return new FileFeedback();
    }

    public MessageFeedback getMessageFeedback() {
        return new MessageFeedback();
    }

    public FileMessageFeedback getFileMessageFeedback() {
        return new FileMessageFeedback();
    }

    public Comment getComment() {
        return new Comment();
    }
}
