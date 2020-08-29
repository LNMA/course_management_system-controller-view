package com.louay.controller.factory;

import com.louay.model.entity.wrapper.AdminRememberMeWrapper;
import com.louay.model.entity.wrapper.StudentHomeWrapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WrappersFactory implements Serializable {
    private static final long serialVersionUID = 5768762391541951182L;

    public StudentHomeWrapper getStudentHomeWrapper() {
        return new StudentHomeWrapper();
    }

    public AdminRememberMeWrapper getAdminRememberMeWrapper() {
        return new AdminRememberMeWrapper();
    }
}
