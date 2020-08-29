package com.louay.controller.factory;

import com.louay.model.service.account.AccountService;
import com.louay.model.service.attendance.UsersAttendanceService;
import com.louay.model.service.authentication.AuthenticationService;
import com.louay.model.service.course.CourseService;
import com.louay.model.service.material.MaterialService;
import com.louay.model.service.member.CourseMemberService;
import com.louay.model.service.role.RoleService;
import com.louay.model.service.status.StatusService;
import com.louay.model.service.userpic.AccountPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
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

    @Autowired
    public ServicesFactory(AccountService accountService, RoleService roleService,
                           AccountPictureService pictureService, StatusService statusService,
                           AuthenticationService authenticationService, CourseService courseService,
                           CourseMemberService courseMemberService, MaterialService materialService,
                           UsersAttendanceService attendanceService) {
        if (accountService == null || roleService == null || pictureService == null || statusService == null ||
                authenticationService == null || courseService == null || courseMemberService == null ||
                materialService == null || attendanceService == null) {
            throw new IllegalArgumentException("Service cannot be null in StudentSignUpServiceWrapper.class!");
        }
        this.accountService = accountService;
        this.roleService = roleService;
        this.pictureService = pictureService;
        this.statusService = statusService;
        this.authenticationService = authenticationService;
        this.courseService = courseService;
        this.courseMemberService = courseMemberService;
        this.materialService = materialService;
        this.attendanceService = attendanceService;
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
}
