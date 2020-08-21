package com.louay.controller.student;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.users.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.PushBuilder;
import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/student/student_home/{email}")
public class StudentHomePageController implements Serializable {
    private static final long serialVersionUID = 4229145274626145763L;
    private EntitiesFactory entitiesFactory;
    private ServicesFactory servicesFactory;

    public StudentHomePageController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        if (entitiesFactory == null || servicesFactory == null){
            throw new IllegalArgumentException("factory cannot be null at StudentHomePageController.class");
        }
        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    public String viewStudentHome(PushBuilder pushBuilder){
        if (pushBuilder != null){
            pushBuilder
                    .path("/static/lib/bootstrap-4.5.1/css/bootstrap.min.css")
                    .path("/static/images/favicon.ico")
                    .path("/static/js/support/modal_forms.js")
                    .path("/static/js/support/dropdown_on_hover.js")
                    .path("/static/lib/angularJS-1.8.0/angular-messages.min.js")
                    .path("/static/lib/angularJS-1.8.0/angular.min.js")
                    .path("/static/lib/popper-2.4.3/popper.min.js")
                    .path("/static/lib/bootstrap-4.5.1/js/bootstrap.min.js")
                    .path("/static/lib/jQuery-3.5.1/jquery-3.5.1.min.js")
                    .path("/WEB-INF/views/student_home.jsp")
                    .push();
        }
        //ModelAndView modelAndView = new ModelAndView("/student_home");
        //modelAndView.addObject("student", findStudent(email));
        return "/student_home";
    }

    private Student findStudent(String email){
        Student student = buildStudent(email);

        return this.servicesFactory.getAccountService().findStudentsDetailsByStudentID(student);
    }

    private Student buildStudent(String email){
        Student student= this.entitiesFactory.getStudent();
        student.setAdmin(this.entitiesFactory.getAdmin());
        student.getAdmin().setEmail(email);
        student.setEmail(email);

        return student;
    }
}
