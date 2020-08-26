package com.louay.controller.factory;

import com.louay.model.entity.wrapper.AdminRememberMeWrapper;
import com.louay.model.entity.wrapper.StudentHomeWrapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WrappersFactory implements Serializable {
    private static final long serialVersionUID = -8302669953452170547L;
    private StudentHomeWrapper studentHomeWrapper;
    private AdminRememberMeWrapper adminRememberMeWrapper;

    public StudentHomeWrapper getStudentHomeWrapper() {
        setStudentHomeWrapper();
        return studentHomeWrapper;
    }

    public void setStudentHomeWrapper() {
        this.studentHomeWrapper = new StudentHomeWrapper();
    }

    public AdminRememberMeWrapper getAdminRememberMeWrapper() {
        setAdminRememberMeWrapper();
        return adminRememberMeWrapper;
    }

    public void setAdminRememberMeWrapper() {
        this.adminRememberMeWrapper = new AdminRememberMeWrapper();
    }
}
