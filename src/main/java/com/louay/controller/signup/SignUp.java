package com.louay.controller.signup;

import com.louay.model.chains.users.Student;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.logging.Logger;

@Controller
public class SignUp implements Serializable {
    private final static Logger LOGGER = Logger.getLogger(SignUp.class.getCanonicalName());
    private static final long serialVersionUID = 1L;

    @GetMapping(value = "/student_sign_up")
    public String display() {
        return "sign-up/student-sign_up.html";
    }

    @RequestMapping(value = "/subStudentSignUp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String studentSignUp(@RequestBody Student student){
        System.out.println(student.getEmail()+student.getPassword()+student.getForename()+student.getSurname()+student.getBirthday()+student.getGender()+student.getCountry()+student.getState()+student.getAddress());
        return "";
    }
}
