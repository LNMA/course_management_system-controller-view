package com.louay.controller.factory;

import com.louay.model.entity.wrapper.AdminRememberMeWrapper;
import com.louay.model.entity.wrapper.StudentHomeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WrappersFactory implements Serializable {
    private static final long serialVersionUID = 4949350062048623251L;
    private StudentHomeWrapper studentHomeWrapper;
    private AdminRememberMeWrapper adminRememberMeWrapper;

    public StudentHomeWrapper getStudentHomeWrapper() {
        return studentHomeWrapper;
    }

    @Autowired
    public void setStudentHomeWrapper(StudentHomeWrapper studentHomeWrapper) {
        if (studentHomeWrapper == null) {
            throw new IllegalArgumentException("studentHomeWrapper entities cannot be null at WrappersFactory.class!.");
        }
        this.studentHomeWrapper = studentHomeWrapper;
    }

    public AdminRememberMeWrapper getAdminRememberMeWrapper() {
        return adminRememberMeWrapper;
    }

    @Autowired
    public void setAdminRememberMeWrapper(AdminRememberMeWrapper adminRememberMeWrapper) {
        if (adminRememberMeWrapper == null) {
            throw new IllegalArgumentException("adminRememberMeWrapper entities cannot be null at WrappersFactory.class!.");
        }
        this.adminRememberMeWrapper = adminRememberMeWrapper;
    }
}
