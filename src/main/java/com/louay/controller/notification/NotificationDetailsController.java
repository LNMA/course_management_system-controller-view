package com.louay.controller.notification;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.feedback.CourseFeedback;
import com.louay.model.entity.material.CourseMaterials;
import com.louay.model.entity.notification.FeedbackNotification;
import com.louay.model.entity.notification.MaterialNotification;
import com.louay.model.entity.notification.constant.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/notification/details")
public class NotificationDetailsController implements Serializable {
    private static final long serialVersionUID = -324945986868948415L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public NotificationDetailsController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @GetMapping(value = "/view_material_notification_page")
    public String viewMaterialNotificationPage() {
        return "/static/html/material_notification.html";
    }

    @GetMapping(value = "/view_feedback_notification_page")
    public String viewFeedbackNotificationPage() {
        return "/static/html/feedback_notification.html";
    }

    @GetMapping(value = "/get_notification_course_material", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<CourseMaterials> getNotificationCourseMaterials(
            @SessionAttribute(value = "id") String emailInSession) {
        Assert.notNull(emailInSession, "emailInSession cannot be null!.");

        Set<CourseMaterials> courseMaterialsSet = buildCourseMaterialsSet(emailInSession);
        return findCourseMaterialsFromIterable(courseMaterialsSet);
    }

    private Set<CourseMaterials> findCourseMaterialsFromIterable(Iterable<CourseMaterials> courseMaterialsIterable) {
        return this.servicesFactory.getMaterialService().findCourseMaterialsByMaterialId(courseMaterialsIterable);
    }

    private Set<CourseMaterials> buildCourseMaterialsSet(String email) {
        Set<MaterialNotification> notificationSet = findMaterialNotificationSet(email);

        Set<CourseMaterials> courseMaterialsSet = new HashSet<>();
        for (MaterialNotification mf : notificationSet) {
            courseMaterialsSet.add(buildCourseMaterials(mf.getCourseMaterials().getMaterialID()));
        }

        return courseMaterialsSet;
    }

    private CourseMaterials buildCourseMaterials(Long materialId) {
        CourseMaterials courseMaterials = this.entitiesFactory.getCourseMaterials();
        courseMaterials.setMaterialID(materialId);

        return courseMaterials;
    }

    private Set<MaterialNotification> findMaterialNotificationSet(String email) {
        MaterialNotification materialNotification = buildMaterialNotification(email);

        return this.servicesFactory.getNotificationService()
                .findNotSeenMaterialNotificationByUserId(materialNotification);
    }

    private MaterialNotification buildMaterialNotification(String email) {
        MaterialNotification materialNotification = this.entitiesFactory.getMaterialNotification();
        materialNotification.setUsers(this.entitiesFactory.getUsers());
        materialNotification.getUsers().setEmail(email);
        materialNotification.setNotificationType(NotificationType.MATERIAL);
        materialNotification.setSeen(false);

        return materialNotification;
    }

    @GetMapping(value = "/get_notification_course_feedback", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<CourseFeedback> getNotificationCourseFeedback(
            @SessionAttribute(value = "id") String emailInSession) {
        Assert.notNull(emailInSession, "emailInSession cannot be null!.");

        Set<CourseFeedback> courseFeedbackSet = buildCourseFeedbackSet(emailInSession);
        return findCourseFeedbackFromIterable(courseFeedbackSet);
    }

    private Set<CourseFeedback> findCourseFeedbackFromIterable(Iterable<CourseFeedback> courseFeedbackIterable) {
        return this.servicesFactory.getFeedbackService().findCourseFeedbackByFeedbackId(courseFeedbackIterable);
    }

    private Set<CourseFeedback> buildCourseFeedbackSet(String email) {
        Set<FeedbackNotification> notificationSet = findFeedbackNotificationSet(email);

        Set<CourseFeedback> courseFeedbackSet = new HashSet<>();
        for (FeedbackNotification fn : notificationSet) {
            courseFeedbackSet.add(buildCourseFeedback(fn.getCourseFeedback().getFeedbackID()));
        }

        return courseFeedbackSet;
    }

    private CourseFeedback buildCourseFeedback(Long feedbackId) {
        CourseFeedback courseFeedback = this.entitiesFactory.getCourseFeedback();
        courseFeedback.setFeedbackID(feedbackId);

        return courseFeedback;
    }

    private Set<FeedbackNotification> findFeedbackNotificationSet(String email) {
        FeedbackNotification feedbackNotification = buildFeedbackNotification(email);

        return this.servicesFactory.getNotificationService()
                .findNotSeenFeedbackNotificationByUserId(feedbackNotification);
    }

    private FeedbackNotification buildFeedbackNotification(String email) {
        FeedbackNotification feedbackNotification = this.entitiesFactory.getFeedbackNotification();
        feedbackNotification.setUsers(this.entitiesFactory.getUsers());
        feedbackNotification.getUsers().setEmail(email);
        feedbackNotification.setNotificationType(NotificationType.FEEDBACK);
        feedbackNotification.setSeen(false);

        return feedbackNotification;
    }
}
