package com.louay.controller.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.PushBuilder;
import java.io.Serializable;
@Controller
@CrossOrigin(origins = "https://localhost:8443")
public class CustomErrorController implements ErrorController, Serializable {
    private static final long serialVersionUID = 6404263372139633257L;

    @Override
    public String getErrorPath() {
        return null;
    }

    @RequestMapping(value = "/error")
    public String viewErrorPage(PushBuilder pushBuilder) {
        if (pushBuilder != null) {
            pushBuilder
                    .path("/static/lib/bootstrap-4.5.1/css/bootstrap.min.css")
                    .path("/static/images/favicon.ico")
                    .path("/static/images/broken_robot.png")
                    .path("/static/lib/bootstrap-4.5.1/js/bootstrap.min.js")
                    .path("/static/lib/popper-2.4.3/popper.min.js")
                    .path("/static/lib/jQuery-3.5.1/jquery-3.5.1.min.js")
                    .path("/WEB-INF/views/error.jsp")
                    .push();
        }
        return "/WEB-INF/views/error.jsp";
    }

}


