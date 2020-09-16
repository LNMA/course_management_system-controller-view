package com.louay.controller.factory;

import com.louay.model.service.account.AccountService;
import com.louay.model.service.attendance.UsersAttendanceService;
import com.louay.model.service.authentication.AuthenticationService;
import com.louay.model.service.comment.CommentService;
import com.louay.model.service.course.CourseService;
import com.louay.model.service.feedback.FeedbackService;
import com.louay.model.service.material.MaterialService;
import com.louay.model.service.member.CourseMemberService;
import com.louay.model.service.notification.NotificationService;
import com.louay.model.service.role.RoleService;
import com.louay.model.service.status.StatusService;
import com.louay.model.service.userpic.AccountPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServicesFactory {
    private final AccountService accountService;
    private final RoleService roleService;
    private final AccountPictureService pictureService;
    private final StatusService statusService;
    private final AuthenticationService authenticationService;
    private final CourseService courseService;
    private final CourseMemberService courseMemberService;
    private final MaterialService materialService;
    private final UsersAttendanceService attendanceService;
    private final FeedbackService feedbackService;
    private final CommentService commentService;
    private final NotificationService notificationService;


    @Autowired
    public ServicesFactory(AccountService accountService, RoleService roleService,
                           AccountPictureService pictureService, StatusService statusService,
                           AuthenticationService authenticationService, CourseService courseService,
                           CourseMemberService courseMemberService, MaterialService materialService,
                           UsersAttendanceService attendanceService, FeedbackService feedbackService,
                           CommentService commentService, NotificationService notificationService) {
        Assert.notNull(accountService, "accountService cannot be null!");
        Assert.notNull(roleService, "roleService cannot be null!");
        Assert.notNull(pictureService, "pictureService cannot be null!");
        Assert.notNull(statusService, "statusService cannot be null!");
        Assert.notNull(authenticationService, "authenticationService cannot be null!");
        Assert.notNull(courseService, "courseService cannot be null!");
        Assert.notNull(courseMemberService, "courseMemberService cannot be null!");
        Assert.notNull(materialService, "materialService cannot be null!");
        Assert.notNull(attendanceService, "attendanceService cannot be null!");
        Assert.notNull(feedbackService, "feedbackService cannot be null!");
        Assert.notNull(commentService, "commentService cannot be null!");
        Assert.notNull(notificationService, "notificationService cannot be null!");

        this.accountService = accountService;
        this.roleService = roleService;
        this.pictureService = pictureService;
        this.statusService = statusService;
        this.authenticationService = authenticationService;
        this.courseService = courseService;
        this.courseMemberService = courseMemberService;
        this.materialService = materialService;
        this.attendanceService = attendanceService;
        this.feedbackService = feedbackService;
        this.commentService = commentService;
        this.notificationService = notificationService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    public AccountPictureService getPictureService() {
        return pictureService;
    }

    public StatusService getStatusService() {
        return statusService;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public CourseMemberService getCourseMemberService() {
        return courseMemberService;
    }

    public MaterialService getMaterialService() {
        return materialService;
    }

    public UsersAttendanceService getAttendanceService() {
        return attendanceService;
    }

    public FeedbackService getFeedbackService() {
        return feedbackService;
    }

    public CommentService getCommentService() {
        return commentService;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }
}
