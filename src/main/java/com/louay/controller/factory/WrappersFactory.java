package com.louay.controller.factory;

import com.louay.model.entity.wrapper.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WrappersFactory implements Serializable {
    private static final long serialVersionUID = -5461444849491744769L;

    public StudentHomeWrapper getStudentHomeWrapper() {
        return new StudentHomeWrapper();
    }

    public AdminRememberMeWrapper getAdminRememberMeWrapper() {
        return new AdminRememberMeWrapper();
    }

    public GeneralSearch getGeneralSearch() {
        return new GeneralSearch();
    }

    public CourseSearch getCourseSearch() {
        return new CourseSearch();
    }

    public InstructorHomeWrapper getInstructorHomeWrapper() {
        return new InstructorHomeWrapper();
    }

}
