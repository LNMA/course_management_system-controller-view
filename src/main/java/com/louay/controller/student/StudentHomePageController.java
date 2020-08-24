package com.louay.controller.student;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.controller.factory.WrappersFactory;
import com.louay.model.entity.users.Student;
import com.louay.model.entity.users.picute.AccountPicture;
import com.louay.model.entity.wrapper.StudentHomeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.PushBuilder;
import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/student/student_home/{email}")
public class StudentHomePageController implements Serializable {
    private static final long serialVersionUID = -6032312962777330181L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;
    private final WrappersFactory wrappersFactory;


    @Autowired
    public StudentHomePageController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory,
                                     WrappersFactory wrappersFactory) {
        if (entitiesFactory == null || servicesFactory == null || wrappersFactory == null) {
            throw new IllegalArgumentException("factory cannot be null at StudentHomePageController.class");
        }
        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
        this.wrappersFactory = wrappersFactory;
    }

    @GetMapping
    public String viewStudentHome() {
        return "/student_home";
    }

    @GetMapping(value = "/myInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    StudentHomeWrapper getStudentHomeInfo(@PathVariable(value = "email") String email) {
        String originalEmail = filterEmailUrlToOriginal(email);

        return buildStudentHomeWrapper(originalEmail);
    }

    private StudentHomeWrapper buildStudentHomeWrapper(String originalEmail) {
        StudentHomeWrapper studentHomeWrapper = this.wrappersFactory.getStudentHomeWrapper();
        studentHomeWrapper.setStudent(findStudent(originalEmail));
        AccountPicture accountPicture = findAccountPicture(originalEmail);
        studentHomeWrapper.setPictureBase64(accountPicture.getBase64());

        return studentHomeWrapper;
    }

    private String filterEmailUrlToOriginal(String urlEmail) {
        int emailLength = urlEmail.length();
        String subEmail = urlEmail.substring(emailLength - 4, emailLength);
        if (subEmail.equals("-com")) {
            return urlEmail.substring(0, emailLength - 4) + ".com";
        }
        return urlEmail;
    }


    public Student findStudent(String email) {
        Student student = buildStudent(email);

        return this.servicesFactory.getAccountService().findStudentsDetailsByStudentID(student);
    }

    private Student buildStudent(String email) {
        Student student = this.entitiesFactory.getStudent();
        student.setAdmin(this.entitiesFactory.getAdmin());
        student.getAdmin().setEmail(email);
        student.setEmail(email);

        return student;
    }

    private AccountPicture findAccountPicture(String email) {
        return this.servicesFactory.getPictureService().findAccountPictureByUserId(buildAccountPicture(email));
    }

    private AccountPicture buildAccountPicture(String email) {
        AccountPicture accountPicture = this.entitiesFactory.getAccountPicture();
        accountPicture.setUsers(buildStudent(email));

        return accountPicture;
    }
}
