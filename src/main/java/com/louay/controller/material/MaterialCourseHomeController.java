package com.louay.controller.material;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.material.FileMaterials;
import com.louay.model.entity.material.MaterialContent;
import com.louay.model.entity.material.TextMaterials;
import com.louay.model.entity.material.constant.MaterialType;
import com.louay.model.entity.notification.MaterialNotification;
import com.louay.model.entity.notification.constant.NotificationType;
import com.louay.model.entity.wrapper.FileMaterialWithOutFile;
import com.louay.model.entity.wrapper.MaterialWithOutContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/course/{courseId}/material")
public class MaterialCourseHomeController implements Serializable {
    private static final long serialVersionUID = -5445081743525567521L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public MaterialCourseHomeController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @GetMapping
    public String viewCourseMaterialHomePage(@SessionAttribute(value = "id", required = false) String emailInSession,
                                             @PathVariable(value = "courseId", required = false) String courseId) {
        if (emailInSession == null || courseId == null) {
            return "redirect:/login";
        }

        Long courseIdNumber = Long.valueOf(courseId);
        Set<MaterialNotification> materialNotificationSet =
                buildSeenMaterialNotificationSet(emailInSession, courseIdNumber);
        updateMaterialNotificationSet(materialNotificationSet);

        return "/static/html/course_material.html";
    }

    private void updateMaterialNotificationSet(Set<MaterialNotification> materialNotificationSet) {
        this.servicesFactory.getNotificationService().updateMaterialNotification(materialNotificationSet);
    }

    private Set<MaterialNotification> buildSeenMaterialNotificationSet(String email, Long courseId) {
        Set<MaterialNotification> notificationSet = findMaterialNotification(email, courseId);

        for (MaterialNotification mn : notificationSet) {
            mn.setSeen(true);
        }

        return notificationSet;
    }

    private Set<MaterialNotification> findMaterialNotification(String email, Long courseId) {
        MaterialNotification materialNotification = buildMaterialNotification(email, courseId);

        return this.servicesFactory.getNotificationService()
                .findNotSeenMaterialNotificationByUserIdAndCourseId(materialNotification);
    }

    private MaterialNotification buildMaterialNotification(String email, Long courseId) {
        MaterialNotification materialNotification = this.entitiesFactory.getMaterialNotification();
        materialNotification.setUsers(this.entitiesFactory.getUsers());
        materialNotification.getUsers().setEmail(email);
        materialNotification.setCourse(this.entitiesFactory.getCourses());
        materialNotification.getCourse().setCourseID(courseId);
        materialNotification.setSeen(false);
        materialNotification.setNotificationType(NotificationType.MATERIAL);

        return materialNotification;
    }

    @GetMapping(value = "/{materialType}/{materialId}/get_content", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public MaterialContent getCourseMaterialWithContent(@PathVariable(name = "materialId") String materialId,
                                                        @PathVariable(name = "materialType") String materialType) {
        Assert.notNull(materialId, "materialId cannot be null!.");
        Long materialIdNumber = Long.valueOf(materialId);

        switch (MaterialType.valueOf(materialType)) {
            case FILE:
                return findFileMaterial(materialIdNumber);

            case TEXT:
                return findTextMaterial(materialIdNumber);

            default:
                throw new UnsupportedOperationException("Material type should only FILE or TEXT");
        }
    }

    private FileMaterials findFileMaterial(Long materialId) {
        FileMaterials fileMaterials = buildFileMaterials();
        fileMaterials.setMaterialID(materialId);

        return this.servicesFactory.getMaterialService().findFileMaterialsByMaterialId(fileMaterials);
    }

    private TextMaterials findTextMaterial(Long materialId) {
        TextMaterials textMaterials = buildTextMaterials();
        textMaterials.setMaterialID(materialId);

        return this.servicesFactory.getMaterialService().findTextMaterialsByMaterialId(textMaterials);
    }

    @GetMapping(value = {"/course_shallow_material"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TreeSet<MaterialWithOutContent> getCourseMaterialWithOutContent(@PathVariable(name = "courseId") String courseId) {
        Assert.notNull(courseId, "courseId cannot be null!.");
        Long courseIdNumber = Long.valueOf(courseId);

        return buildMaterialContentTreeSet(courseIdNumber);
    }

    private TreeSet<MaterialWithOutContent> buildMaterialContentTreeSet(Long courseId) {
        TextMaterials textMaterials = buildTextMaterials();
        textMaterials.getCourse().setCourseID(courseId);

        FileMaterials fileMaterials = buildFileMaterials();
        fileMaterials.getCourse().setCourseID(courseId);

        TreeSet<MaterialWithOutContent> contentTreeSet = new TreeSet<>();
        Set<FileMaterialWithOutFile> fileMaterialSet = findExistFileMaterialWithoutFile(fileMaterials);
        Set<MaterialWithOutContent> textMaterialSet = findExistTextMaterialWithoutText(textMaterials);
        if (!textMaterialSet.isEmpty()) {
            contentTreeSet.addAll(textMaterialSet);
        }
        if (!fileMaterialSet.isEmpty()) {
            contentTreeSet.addAll(fileMaterialSet);
        }
        return contentTreeSet;
    }

    private Set<FileMaterialWithOutFile> findExistFileMaterialWithoutFile(FileMaterials fileMaterials) {
        if (isFileMaterialExistToThisCourse(fileMaterials)) {
            return this.servicesFactory.getMaterialService()
                    .findFileMaterialWithoutFileByCourseId(fileMaterials);
        }
        return new HashSet<>();
    }

    private Set<MaterialWithOutContent> findExistTextMaterialWithoutText(TextMaterials textMaterials) {
        if (isTextMaterialExistToThisCourse(textMaterials)) {
            return this.servicesFactory.getMaterialService()
                    .findTextMaterialWithoutTextByCourseId(textMaterials);
        }
        return new HashSet<>();
    }

    private Boolean isFileMaterialExistToThisCourse(FileMaterials fileMaterials) {
        return this.servicesFactory.getMaterialService()
                .isFileMaterialExistByCourseId(fileMaterials);

    }

    private Boolean isTextMaterialExistToThisCourse(TextMaterials textMaterials) {

        return this.servicesFactory.getMaterialService()
                .isTextMaterialExistByCourseId(textMaterials);
    }

    private FileMaterials buildFileMaterials() {
        FileMaterials fileMaterials = this.entitiesFactory.getFileMaterials();
        fileMaterials.setCourse(this.entitiesFactory.getCourses());
        fileMaterials.setUser(this.entitiesFactory.getUsers());

        return fileMaterials;
    }

    private TextMaterials buildTextMaterials() {
        TextMaterials textMaterials = this.entitiesFactory.getTextMaterials();
        textMaterials.setCourse(this.entitiesFactory.getCourses());
        textMaterials.setUser(this.entitiesFactory.getUsers());

        return textMaterials;
    }
}
