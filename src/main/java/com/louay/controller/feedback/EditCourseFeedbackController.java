package com.louay.controller.feedback;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.feedback.CourseFeedback;
import com.louay.model.entity.feedback.FileFeedback;
import com.louay.model.entity.feedback.FileMessageFeedback;
import com.louay.model.entity.feedback.MessageFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/course/{courseId}/feedback/{feedbackId}/edit-feedback")
public class EditCourseFeedbackController implements Serializable {
    private static final long serialVersionUID = -4393511517072335131L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public EditCourseFeedbackController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @GetMapping
    public String viewEditFeedbackPage() {
        return "/static/html/edit_post-form.html";
    }

    @PostMapping(value = "/update_file-text_post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateFileTextPost(@PathVariable(value = "feedbackId") String feedbackId,
                                     @RequestParam(value = "file") MultipartFile multipartFile,
                                     @RequestParam(value = "textFeedback") String textFeedback) {

        Assert.notNull(feedbackId, "feedback id cannot be null!.");
        Assert.notNull(textFeedback, "textFeedback cannot be null!.");

        if (multipartFile.isEmpty() || multipartFile.getSize() > (1024 * 1024 * 4) ||
                !Objects.requireNonNull(multipartFile.getContentType()).contains("image")) {
            throw new IllegalStateException("It's seem there something wrong in while process this operation !.");
        }

        Long feedbackIdNumber = Long.valueOf(feedbackId);


        CourseFeedback courseFeedback = null;
        try {
            courseFeedback = updateCourseFeedback(
                    buildCourseFeedbackToFileMessageFeedback(multipartFile, textFeedback, feedbackIdNumber));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Assert.notNull(courseFeedback, "course feedback cannot be null!.");
        return String.format("redirect:/course/%d/feedback", courseFeedback.getCourse().getCourseID());
    }

    private CourseFeedback buildCourseFeedbackToFileMessageFeedback(MultipartFile multipartFile, String textFeedback,
                                                                    Long feedbackId) throws IOException {
        CourseFeedback courseFeedback = findCourseFeedback(feedbackId);
        ((FileMessageFeedback) courseFeedback.getFeedbackContent()).setFile(multipartFile.getBytes());
        ((FileMessageFeedback) courseFeedback.getFeedbackContent()).setFileExtension(multipartFile.getOriginalFilename());
        ((FileMessageFeedback) courseFeedback.getFeedbackContent()).setPostMessage(textFeedback);

        return courseFeedback;
    }

    @PostMapping(value = "/update_file_post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateFilePost(@PathVariable(value = "feedbackId") String feedbackId,
                                 @RequestParam(value = "file") MultipartFile multipartFile) {

        Assert.notNull(feedbackId, "feedback id cannot be null!.");

        if (multipartFile.isEmpty() || multipartFile.getSize() > (1024 * 1024 * 4) ||
                !Objects.requireNonNull(multipartFile.getContentType()).contains("image")) {
            throw new IllegalStateException("It's seem there something wrong in while process this operation !.");
        }

        Long feedbackIdNumber = Long.valueOf(feedbackId);


        CourseFeedback courseFeedback = null;
        try {
            courseFeedback = updateCourseFeedback(buildCourseFeedbackToFileFeedback(multipartFile, feedbackIdNumber));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Assert.notNull(courseFeedback, "course feedback cannot be null!.");
        return String.format("redirect:/course/%d/feedback", courseFeedback.getCourse().getCourseID());
    }

    private CourseFeedback buildCourseFeedbackToFileFeedback(MultipartFile multipartFile, Long feedbackId)
            throws IOException {
        CourseFeedback courseFeedback = findCourseFeedback(feedbackId);
        ((FileFeedback) courseFeedback.getFeedbackContent()).setFile(multipartFile.getBytes());
        ((FileFeedback) courseFeedback.getFeedbackContent()).setFileExtension(multipartFile.getOriginalFilename());

        return courseFeedback;
    }

    @RequestMapping(value = "/update_text_post", consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.PUT)
    public ResponseEntity<String> updateTextPost(@RequestBody MessageFeedback messageFeedback,
                                         @PathVariable(value = "feedbackId") String feedbackId) {
        Assert.notNull(feedbackId, "feedback id cannot be null!.");
        Assert.notNull(messageFeedback.getPostMessage(), "post content cannot be null!.");

        Long feedbackIdNumber = Long.valueOf(feedbackId);

        CourseFeedback courseFeedback =
                updateCourseFeedback(buildCourseFeedbackToMessageFeedback(messageFeedback, feedbackIdNumber));

        return ResponseEntity.ok().body(String.format("/course/%d/feedback", courseFeedback.getCourse().getCourseID()));
    }

    private CourseFeedback updateCourseFeedback(CourseFeedback courseFeedback) {
        return this.servicesFactory.getFeedbackService().createCourseFeedback(courseFeedback);
    }

    private CourseFeedback buildCourseFeedbackToMessageFeedback(MessageFeedback messageFeedback, Long feedbackId) {
        CourseFeedback courseFeedback = findCourseFeedback(feedbackId);
        ((MessageFeedback) courseFeedback.getFeedbackContent()).setPostMessage(messageFeedback.getPostMessage());

        return courseFeedback;
    }

    @GetMapping(value = "/show_target_feedback", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CourseFeedback getCourseFeedback(@PathVariable(value = "feedbackId") String feedbackId) {
        Assert.notNull(feedbackId, "feedback id cannot be null!.");

        Long feedbackIdNumber = Long.valueOf(feedbackId);

        return findCourseFeedback(feedbackIdNumber);
    }

    private CourseFeedback findCourseFeedback(Long feedbackId) {

        return this.servicesFactory.getFeedbackService()
                .findCourseFeedbackByFeedbackId(buildCourseFeedback(feedbackId));
    }

    private CourseFeedback buildCourseFeedback(Long feedbackId) {
        CourseFeedback courseFeedback = this.entitiesFactory.getCourseFeedback();
        courseFeedback.setFeedbackID(feedbackId);

        return courseFeedback;
    }

}
