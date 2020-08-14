package com.louay.controller.signup;

import com.louay.model.entity.users.Admin;
import com.louay.model.entity.users.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.PushBuilder;
import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
public class SignUp implements Serializable {
    private static final long serialVersionUID = 5131323790457804807L;

    @RequestMapping(value = "/student-sign_up")
    public String display(PushBuilder pushBuilder) {
        if (null != pushBuilder) {
            pushBuilder.method("GET");
            pushBuilder.path("/static/css/register.css")
                    .addHeader("content-type", "text/css").push();

            pushBuilder.path("/WEB-INF/jsp/sign_up.jsp")
                    .addHeader("content-type", "text/html").push();
        }
        return "/sign_up";
    }

    @RequestMapping(value = "/subStudentSignUp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void studentSignUp(@Validated @RequestBody Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(System.out::println);
        }
        System.out.println(student.toString());
        System.out.println(student.getEmail());
        System.out.println(student.getAdmin().getPassword());
    }
}
