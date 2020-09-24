package com.louay.controller.course;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.courses.Courses;
import com.louay.model.entity.users.Instructor;
import com.louay.model.util.file.FileProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping("/course")
public class CreateEditCourseController implements Serializable {
    private static final long serialVersionUID = -1932842080298580610L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;
    private final FileProcess fileProcess;

    @Autowired
    public CreateEditCourseController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory,
                                      FileProcess fileProcess) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");
        Assert.notNull(fileProcess, "fileProcess cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
        this.fileProcess = fileProcess;
    }

    @RequestMapping(value = "/{courseId}/update_course/course_end_date", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    @ResponseBody
    public Calendar updateCourseEndDate(@PathVariable(value = "courseId") String courseId,
                                        @RequestBody Courses courses) {

        Assert.notNull(courseId, "courseId cannot be null!.");
        Assert.notNull(courses.getEndDate(), "course end date cannot be null!.");

        Long courseIdNumber = Long.valueOf(courseId);
        Courses coursesEntity = findCourse(courseIdNumber);
        coursesEntity.setEndDate(courses.getEndDate());
        coursesEntity = updateCourse(coursesEntity);

        return coursesEntity.getEndDate();
    }

    @RequestMapping(value = "/{courseId}/update_course/course_name", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    @ResponseBody
    public String updateCourseName(@PathVariable(value = "courseId") String courseId,
                                   @RequestBody Courses courses) {

        Assert.notNull(courseId, "courseId cannot be null!.");
        Assert.notNull(courses.getCourseName(), "course name cannot be null!.");

        Long courseIdNumber = Long.valueOf(courseId);
        Courses coursesEntity = findCourse(courseIdNumber);
        coursesEntity.setCourseName(courses.getCourseName());
        coursesEntity = updateCourse(coursesEntity);

        return coursesEntity.getCourseName();
    }

    @PostMapping(value = "/{courseId}/update_course/course_picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateCoursePicture(@PathVariable(value = "courseId") String courseId,
                                      @RequestParam(value = "file") MultipartFile multipartFile) {

        Assert.notNull(courseId, "courseId cannot be null!.");
        Assert.notNull(multipartFile, "file cannot be null!.");

        if (multipartFile.isEmpty() || multipartFile.getSize() > (1024 * 1024 * 4) ||
                !Objects.requireNonNull(multipartFile.getContentType()).contains("image")) {
            throw new IllegalStateException("It's seem there something wrong in while process this operation !.");
        }

        Long courseIdNumber = Long.valueOf(courseId);
        Courses courses = findCourse(courseIdNumber);

        try {
            courses.setPicture(multipartFile.getBytes());
            courses = updateCourse(courses);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Assert.notNull(courses, "course cannot be null!.");
        return String.format("redirect:/course/%d", courses.getCourseID());
    }

    private Courses updateCourse(Courses course) {
        return this.servicesFactory.getCourseService().updateCourse(course);
    }

    private Courses findCourse(Long courseId) {
        Courses courses = buildCourse(courseId);

        return this.servicesFactory.getCourseService().findCourseByCourseId(courses);
    }

    private Courses buildCourse(Long courseId) {
        Courses courses = this.entitiesFactory.getCourses();
        courses.setCourseID(courseId);

        return courses;
    }

    @PostMapping(value = "/add_course", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addCourse(@RequestBody Courses courses,
                                            @SessionAttribute(value = "id", required = false) String emailInSession) {
        Assert.notNull(courses.getCourseName(), "course name cannot be null!.");
        Assert.notNull(courses.getEndDate(), "end date cannot be null!.");
        Assert.notNull(emailInSession, "it's seem you are not authenticated, go and login NOW!.");

        Courses coursesEntity = buildCourse(emailInSession, courses.getCourseName(), courses.getEndDate());
        coursesEntity = createCourse(coursesEntity);

        return ResponseEntity.ok().body(String.format("Course created successfully, it's id %s",
                coursesEntity.getCourseID()));
    }

    private Courses createCourse(Courses courses) {
        return this.servicesFactory.getCourseService().createCourse(courses);
    }

    private Courses buildCourse(String email, String courseName, Calendar endDate) {
        Courses courses = this.entitiesFactory.getCourses();
        Instructor instructor = buildInstructor(email);
        courses.setInstructor(instructor);
        courses.setPicture(getDefaultPicture());
        courses.setStartDate(Calendar.getInstance());
        courses.setCourseName(courseName);
        courses.setEndDate(endDate);

        return courses;
    }

    private Instructor buildInstructor(String email) {
        Instructor instructor = this.entitiesFactory.getInstructor();
        instructor.setEmail(email);

        return instructor;
    }

    private byte[] getDefaultPicture() {
        byte[] pictureBytes = null;
        try {
            FileUrlResource fileUrlResource = new FileUrlResource("src/main/webapp/static/images/course_picture.jpg");
            pictureBytes = this.fileProcess.readFile(fileUrlResource.getFile().getAbsolutePath());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return pictureBytes;
    }

}
