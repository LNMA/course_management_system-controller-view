package com.louay.controller.feedback;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.courses.Courses;
import com.louay.model.entity.courses.members.CourseMembers;
import com.louay.model.entity.feedback.*;
import com.louay.model.entity.feedback.constant.FeedbackType;
import com.louay.model.entity.notification.FeedbackNotification;
import com.louay.model.entity.notification.constant.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/course/{courseId}/feedback")
public class CourseFeedbackController implements Serializable {
    private static final long serialVersionUID = 4335802874333497100L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public CourseFeedbackController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @GetMapping
    public String viewCourseFeedbackPage() {
        return "/static/html/course_feedback.html";
    }

    @PostMapping(value = "/delete_post", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String deletePost(@RequestBody CourseFeedback courseFeedback,
                             @PathVariable(value = "courseId") String courseId) {
        if (courseFeedback.getFeedbackID() == null) {
            return "redirect:/login";
        }

        this.servicesFactory.getFeedbackService().deleteCourseFeedbackByFeedbackId(courseFeedback);
        return String.format("redirect:/course/%s/feedback", courseId);
    }

    @PostMapping(value = "/add_file-text_post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createFileTextPost(@SessionAttribute(value = "id", required = false) String email,
                                     @PathVariable(value = "courseId") String courseId,
                                     @RequestParam(value = "file") MultipartFile multipartFile,
                                     @RequestParam(value = "textFeedback") String textFeedback) {
        if (email == null || courseId == null) {
            return "redirect:/login";
        }

        if (multipartFile.isEmpty() || multipartFile.getSize() > (1024 * 1024 * 4) ||
                !Objects.requireNonNull(multipartFile.getContentType()).contains("image")) {
            throw new RuntimeException("It's seem there something wrong in while process this operation !.");
        }

        Long courseIdNumber = Long.valueOf(courseId);
        CourseFeedback courseFeedback = null;
        try {
            courseFeedback =
                    createCourseFeedbackWithContent(buildCourseFeedbackToFileTextFeedback(multipartFile, textFeedback,
                            courseIdNumber, email));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Assert.notNull(courseFeedback, "courseFeedback cannot be null!.");

        List<FeedbackNotification> feedbackNotificationSet =
                buildFeedbackNotificationList(courseIdNumber, courseFeedback.getFeedbackID(), email);

        createFeedbackNotification(feedbackNotificationSet);

        return String.format("redirect:/course/%s/feedback", courseId);
    }

    private CourseFeedback buildCourseFeedbackToFileTextFeedback(MultipartFile multipartFile, String textFeedback,
                                                                 Long courseId, String email) throws IOException {
        FileMessageFeedback fileMessageFeedback =
                buildFileMessageFeedback(multipartFile.getBytes(), multipartFile.getOriginalFilename(), textFeedback);

        CourseFeedback courseFeedback = buildCourseFeedback(fileMessageFeedback);
        courseFeedback.setFeedbackType(FeedbackType.ALL);
        courseFeedback.getCourse().setCourseID(courseId);
        courseFeedback.getUser().setEmail(email);

        fileMessageFeedback.setCourseFeedback(courseFeedback);

        return courseFeedback;
    }

    private FileMessageFeedback buildFileMessageFeedback(byte[] file, String fileName, String textFeedback) {
        FileMessageFeedback fileMessageFeedback = this.entitiesFactory.getFileMessageFeedback();
        fileMessageFeedback.setFile(file);
        fileMessageFeedback.setFileExtension(fileName);
        fileMessageFeedback.setPostMessage(textFeedback);

        return fileMessageFeedback;
    }


    @PostMapping(value = "/add_file_post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createFilePost(@SessionAttribute(value = "id", required = false) String email,
                                 @PathVariable(value = "courseId") String courseId,
                                 @RequestParam(value = "file") MultipartFile multipartFile) {
        if (email == null || courseId == null) {
            return "redirect:/login";
        }

        if (multipartFile.isEmpty() || multipartFile.getSize() > (1024 * 1024 * 4) ||
                !Objects.requireNonNull(multipartFile.getContentType()).contains("image")) {
            throw new RuntimeException("It's seem there something wrong in while process this operation !.");
        }

        Long courseIdNumber = Long.valueOf(courseId);
        CourseFeedback courseFeedback = null;

        try {
            courseFeedback =
                    createCourseFeedbackWithContent(buildCourseFeedbackToFileFeedback(multipartFile, courseIdNumber, email));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Assert.notNull(courseFeedback, "courseFeedback cannot be null!.");

        List<FeedbackNotification> feedbackNotificationSet =
                buildFeedbackNotificationList(courseIdNumber, courseFeedback.getFeedbackID(), email);

        createFeedbackNotification(feedbackNotificationSet);

        return String.format("redirect:/course/%s/feedback", courseId);
    }

    private CourseFeedback buildCourseFeedbackToFileFeedback(MultipartFile multipartFile, Long courseId, String email)
            throws IOException {
        FileFeedback fileFeedback = buildFileFeedback(multipartFile.getBytes(), multipartFile.getOriginalFilename());

        CourseFeedback courseFeedback = buildCourseFeedback(fileFeedback);
        courseFeedback.setFeedbackType(FeedbackType.FILE);
        courseFeedback.getCourse().setCourseID(courseId);
        courseFeedback.getUser().setEmail(email);

        fileFeedback.setCourseFeedback(courseFeedback);

        return courseFeedback;
    }

    private FileFeedback buildFileFeedback(byte[] file, String fileName) {
        FileFeedback fileFeedback = this.entitiesFactory.getFileFeedback();
        fileFeedback.setFile(file);
        fileFeedback.setFileExtension(fileName);

        return fileFeedback;
    }

    @PostMapping(value = "/add_text_post", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createTextPost(@RequestBody MessageFeedback messageFeedback,
                                 @PathVariable(value = "courseId") String courseId,
                                 @SessionAttribute(value = "id", required = false) String emailInSession) {

        if (emailInSession == null || courseId == null) {
            return "redirect:/login";
        }
        Long courseIdNumber = Long.valueOf(courseId);
        String textFeedback = messageFeedback.getPostMessage();

        CourseFeedback courseFeedback =
                createCourseFeedbackWithContent(buildCourseFeedbackToMessageFeedback(textFeedback, courseIdNumber, emailInSession));

        List<FeedbackNotification> feedbackNotificationSet =
                buildFeedbackNotificationList(courseIdNumber, courseFeedback.getFeedbackID(), emailInSession);

        createFeedbackNotification(feedbackNotificationSet);
        return String.format("redirect:/course/%d/feedback", courseIdNumber);
    }

    private CourseFeedback createCourseFeedbackWithContent(CourseFeedback courseFeedback) {
        return this.servicesFactory.getFeedbackService().createCourseFeedback(courseFeedback);
    }

    private CourseFeedback buildCourseFeedbackToMessageFeedback(String textFeedback, Long courseId, String email) {
        MessageFeedback messageFeedback = buildMessageFeedback(textFeedback);

        CourseFeedback courseFeedback = buildCourseFeedback(messageFeedback);
        courseFeedback.setFeedbackType(FeedbackType.MESSAGE);
        courseFeedback.getCourse().setCourseID(courseId);
        courseFeedback.getUser().setEmail(email);

        messageFeedback.setCourseFeedback(courseFeedback);

        return courseFeedback;
    }

    private MessageFeedback buildMessageFeedback(String textFeedback) {
        MessageFeedback messageFeedback = this.entitiesFactory.getMessageFeedback();
        messageFeedback.setPostMessage(textFeedback);

        return messageFeedback;
    }

    private CourseFeedback buildCourseFeedback(FeedbackContent feedbackContent) {
        CourseFeedback courseFeedback = this.entitiesFactory.getCourseFeedback();
        courseFeedback.setCourse(this.entitiesFactory.getCourses());
        courseFeedback.setUser(this.entitiesFactory.getUsers());
        courseFeedback.setFeedbackContent(feedbackContent);

        return courseFeedback;
    }

    private void createFeedbackNotification(Iterable<FeedbackNotification> notificationIterable) {
        this.servicesFactory.getNotificationService().createFeedbackNotificationFromIterable(notificationIterable);
    }

    private List<FeedbackNotification> buildFeedbackNotificationList(Long courseId, Long feedbackId, String loginEmail) {
        Set<CourseMembers> courseMembersSet = findCourseMembers(courseId);
        Courses courses = findCourse(courseId);

        List<FeedbackNotification> notificationList = new LinkedList<>();

        for (CourseMembers c : courseMembersSet) {
            if (!loginEmail.equalsIgnoreCase(c.getStudent().getEmail())) {
                notificationList.add(buildFeedbackNotification(c.getStudent().getEmail(), courseId, feedbackId));
            }
        }
        notificationList.add(buildFeedbackNotification(courses.getInstructor().getEmail(), courseId, feedbackId));

        return notificationList;
    }

    private FeedbackNotification buildFeedbackNotification(String email, Long courseId, Long feedbackId) {
        FeedbackNotification feedbackNotification = this.entitiesFactory.getFeedbackNotification();
        feedbackNotification.setCourse(buildCourse(courseId));
        feedbackNotification.setUsers(this.entitiesFactory.getUsers());
        feedbackNotification.getUsers().setEmail(email);
        feedbackNotification.setCourseFeedback(this.entitiesFactory.getCourseFeedback());
        feedbackNotification.getCourseFeedback().setFeedbackID(feedbackId);
        feedbackNotification.setSeen(false);
        feedbackNotification.setNotificationType(NotificationType.FEEDBACK);

        return feedbackNotification;
    }

    private Set<CourseMembers> findCourseMembers(Long courseId) {
        CourseMembers courseMembers = buildCourseMembers(courseId);

        return this.servicesFactory.getCourseMemberService().findLazyCourseMemberByCourseId(courseMembers);
    }

    private CourseMembers buildCourseMembers(Long courseId) {
        CourseMembers courseMembers = this.entitiesFactory.getCourseMembers();
        courseMembers.setCourse(buildCourse(courseId));

        return courseMembers;
    }

    @GetMapping(value = "/feedback_data")
    @ResponseBody
    public TreeSet<CourseFeedback> getFeedbackPostSet(
            @PathVariable(name = "courseId", required = false) String courseId) {

        Assert.notNull(courseId, "courseId cannot be null!.");
        Long courseIdNumber = Long.valueOf(courseId);

        return findCourseFeedbackAndCommentToOneCourse(courseIdNumber);
    }

    private TreeSet<CourseFeedback> findCourseFeedbackAndCommentToOneCourse(Long courseId) {
        CourseFeedback courseFeedback = buildCourseFeedback(courseId);
        Set<CourseFeedback> courseFeedbackSet = this.servicesFactory.getFeedbackService()
                .findCourseFeedbackAndCommentByCourseId(courseFeedback);

        TreeSet<CourseFeedback> feedbackTreeSet = new TreeSet<>();
        if (!courseFeedbackSet.isEmpty()) {
            feedbackTreeSet.addAll(courseFeedbackSet);
        }

        return feedbackTreeSet;
    }

    private CourseFeedback buildCourseFeedback(Long courseId) {
        CourseFeedback courseFeedback = this.entitiesFactory.getCourseFeedback();
        courseFeedback.setCourse(buildCourse(courseId));

        return courseFeedback;
    }

    private Courses findCourse(Long courseId) {
        return this.servicesFactory.getCourseService().findCourseByCourseId(buildCourse(courseId));
    }

    private Courses buildCourse(Long courseId) {
        Courses courses = this.entitiesFactory.getCourses();
        courses.setInstructor(this.entitiesFactory.getInstructor());
        courses.setCourseID(courseId);

        return courses;
    }

}
