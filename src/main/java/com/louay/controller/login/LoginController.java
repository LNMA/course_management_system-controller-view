package com.louay.controller.login;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.wrapper.AdminRememberMeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.PushBuilder;
import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
public class LoginController implements Serializable {
    private static final long serialVersionUID = 4522070934197754540L;
    private final ServicesFactory servicesFactory;
    private final EntitiesFactory entitiesFactory;

    @Autowired
    public LoginController(ServicesFactory servicesFactory, EntitiesFactory entitiesFactory) {
        if (servicesFactory == null || entitiesFactory == null) {
            throw new IllegalArgumentException("factory cannot be null at LoginController.class");
        }
        this.servicesFactory = servicesFactory;
        this.entitiesFactory = entitiesFactory;
    }

    @GetMapping(value = "/login")
    public String viewLoginPage(PushBuilder pushBuilder) {
        if (pushBuilder != null) {
            pushBuilder
                    .path("/static/images/background_geometry.png")
                    .path("/static/css/login.css")
                    .path("/static/css/register.css")
                    .path("/static/lib/bootstrap-4.5.1/css/bootstrap.min.css")
                    .path("/static/images/favicon.ico")
                    .path("/static/js/service/login_submit.js")
                    .path("/static/js/control/login.js")
                    .path("/static/js/app.js")
                    .path("/static/lib/angularJS-1.8.0/angular-messages.min.js")
                    .path("/static/lib/popper-2.4.3/popper.min.js")
                    .path("/static/lib/bootstrap-4.5.1/js/bootstrap.min.js")
                    .path("/static/lib/jQuery-3.5.1/jquery-3.5.1.min.js")
                    .path("/WEB-INF/jsp/login.jsp")
                    .push();
        }
        return "/login";
    }

    @PostMapping(value = "/perform_login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void loginProcess(@RequestBody AdminRememberMeWrapper adminRememberMeWrapper) {
        System.out.println(adminRememberMeWrapper.toString());
    }

    private void isAccountExist(AdminRememberMeWrapper adminRememberMeWrapper){

    }
}
