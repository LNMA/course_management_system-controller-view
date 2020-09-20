package com.louay.controller.feedback;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.feedback.comment.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
public class CommentController implements Serializable {
    private static final long serialVersionUID = -4132727550612126195L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public CommentController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @RequestMapping(value = "/course/{courseId}/feedback/comment/remove_comment",
            consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public ResponseEntity<String> removeComment(@RequestBody Comment comment,
                                                @PathVariable(value = "courseId") String courseId) {

        Assert.notNull(comment.getCommentID(), "commentId cannot be null!.");

        deleteComment(comment);

        return ResponseEntity.ok().body(String.format("/course/%s/feedback", courseId));
    }

    private void deleteComment(Comment comment) {
        this.servicesFactory.getCommentService().deleteCommentByCommentId(comment);
    }


    @PostMapping(value = "/course/{courseId}/feedback/comment/add_comment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addComment(@RequestBody Comment comment,
                             @SessionAttribute(value = "id", required = false) String email,
                             @PathVariable(value = "courseId") String courseId) {
        if (email == null) {
            return "redirect:/login";
        }
        Assert.notNull(comment.getCommentMessage(), "commentText cannot be null!.");
        Assert.notNull(comment.getCourseFeedback().getFeedbackID(), "feedbackId cannot be null!.");

        String commentText = comment.getCommentMessage();
        Long feedbackId = comment.getCourseFeedback().getFeedbackID();

        Comment commentPersist = buildComment(email, feedbackId);
        commentPersist.setCommentMessage(commentText);
        createComment(commentPersist);

        return String.format("redirect:/course/%s/feedback", courseId);
    }

    private void createComment(Comment comment) {
        this.servicesFactory.getCommentService().createComment(comment);
    }

    private Comment buildComment(String email, Long feedbackId) {
        Comment comment = this.entitiesFactory.getComment();
        comment.setCourseFeedback(this.entitiesFactory.getCourseFeedback());
        comment.getCourseFeedback().setFeedbackID(feedbackId);
        comment.setUser(this.entitiesFactory.getUsers());
        comment.getUser().setEmail(email);

        return comment;
    }
}
