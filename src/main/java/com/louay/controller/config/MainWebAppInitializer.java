package com.louay.controller.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class MainWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(final ServletContext sc) throws ServletException {

        AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();

        root.setServletContext(sc);
        root.register(WebConfig.class);
        root.scan("com.louay.controller");
        root.refresh();
        sc.addListener(new ContextLoaderListener(root));

        ServletRegistration.Dynamic appServlet = sc.addServlet("dispatcher", new DispatcherServlet(root));
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/");
    }
}
