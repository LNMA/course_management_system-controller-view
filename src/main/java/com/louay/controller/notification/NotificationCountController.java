package com.louay.controller.notification;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.notification.FeedbackNotification;
import com.louay.model.entity.notification.MaterialNotification;
import com.louay.model.entity.notification.UserNotification;
import com.louay.model.entity.notification.constant.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/notification")
public class NotificationCountController implements Serializable {
    private static final long serialVersionUID = -1948797297422838295L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public NotificationCountController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @GetMapping(value = "/get_material_notification_count", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Long getMaterialNotificationCount(@SessionAttribute(value = "id") String emailInSession) {
        Assert.notNull(emailInSession, "emailInSession cannot be null!.");

        return getCountMaterialNotification(emailInSession);
    }

    private Long getCountMaterialNotification(String email) {
        MaterialNotification materialNotification = buildMaterialNotification(email);

        return this.servicesFactory.getNotificationService()
                .getCountNotSeenMaterialNotificationByUserId(materialNotification);
    }

    private MaterialNotification buildMaterialNotification(String email) {
        MaterialNotification materialNotification = this.entitiesFactory.getMaterialNotification();
        materialNotification.setUsers(this.entitiesFactory.getUsers());
        materialNotification.getUsers().setEmail(email);
        materialNotification.setNotificationType(NotificationType.MATERIAL);
        materialNotification.setSeen(false);

        return materialNotification;
    }

    @GetMapping(value = "/get_feedback_notification_count", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Long getFeedbackNotificationCount(@SessionAttribute(value = "id") String emailInSession) {
        Assert.notNull(emailInSession, "emailInSession cannot be null!.");

        return getCountFeedbackNotification(emailInSession);
    }

    private Long getCountFeedbackNotification(String email) {
        FeedbackNotification feedbackNotification = buildFeedbackNotification(email);

        return this.servicesFactory.getNotificationService()
                .getCountNotSeenFeedbackNotificationByUserId(feedbackNotification);
    }

    private FeedbackNotification buildFeedbackNotification(String email) {
        FeedbackNotification feedbackNotification = this.entitiesFactory.getFeedbackNotification();
        feedbackNotification.setUsers(this.entitiesFactory.getUsers());
        feedbackNotification.getUsers().setEmail(email);
        feedbackNotification.setNotificationType(NotificationType.FEEDBACK);
        feedbackNotification.setSeen(false);

        return feedbackNotification;
    }

    @GetMapping(value = "/get_user_notification_count", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Long getUserNotificationCount(@SessionAttribute(value = "id") String emailInSession) {
        Assert.notNull(emailInSession, "emailInSession cannot be null!.");

        return getCountUserNotification(emailInSession);
    }

    private Long getCountUserNotification(String email) {
        UserNotification userNotification = buildUserNotification(email);

        return this.servicesFactory.getNotificationService()
                .getCountNotSeenUserNotificationByUserId(userNotification);
    }

    private UserNotification buildUserNotification(String email) {
        UserNotification userNotification = this.entitiesFactory.getUserNotification();
        userNotification.setUsers(this.entitiesFactory.getUsers());
        userNotification.getUsers().setEmail(email);
        userNotification.setSeen(false);

        return userNotification;
    }
}
