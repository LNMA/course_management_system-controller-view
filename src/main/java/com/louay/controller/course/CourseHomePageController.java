package com.louay.controller.course;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.controller.factory.WrappersFactory;
import com.louay.model.entity.material.FileMaterials;
import com.louay.model.entity.material.MaterialContent;
import com.louay.model.entity.material.TextMaterials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.*;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/course/{courseId}")
public class CourseHomePageController implements Serializable {
    private static final long serialVersionUID = -3084164645574862522L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;
    private final WrappersFactory wrappersFactory;

    @Autowired
    public CourseHomePageController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory,
                                    WrappersFactory wrappersFactory) {
        if (entitiesFactory == null || servicesFactory == null || wrappersFactory == null) {
            throw new IllegalArgumentException("factory cannot be null at StudentCourseController.class");
        }
        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
        this.wrappersFactory = wrappersFactory;
    }

    @GetMapping
    public String viewCourseHomePage() {
        return "/static/html/course_home.html";
    }

    @GetMapping(value = "/course_shallow_material", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TreeSet<MaterialContent> getCourseMaterialWithOutContent(@PathVariable(name = "courseId") String courseId) {
        long courseIdNumber = 0L;
        if (courseId != null) {
            courseIdNumber = Long.parseLong(courseId);
        }
        return buildMaterialContentTreeSet(courseIdNumber);
    }

    private TreeSet<MaterialContent> buildMaterialContentTreeSet(Long courseId) {
        TreeSet<MaterialContent> contentTreeSet = new TreeSet<>();
        Set<FileMaterials> fileMaterialSet = findExistFileMaterialWithoutFile(courseId);
        Set<TextMaterials> textMaterialSet = findExistTextMaterialWithoutText(courseId);
        if (!fileMaterialSet.isEmpty()) {
            contentTreeSet.addAll(fileMaterialSet);
        }
        if (!textMaterialSet.isEmpty()) {
            contentTreeSet.addAll(textMaterialSet);
        }
        if (contentTreeSet.isEmpty()) {
            TextMaterials textMaterials = buildTextMaterials(courseId);
            textMaterials.setMaterialName("There are nothing yet!.");
            textMaterials.setMaterialDate(Calendar.getInstance());
            textMaterials.getUser().setEmail("System");
            contentTreeSet.add(textMaterials);
        }
        return contentTreeSet;
    }

    private Set<FileMaterials> findExistFileMaterialWithoutFile(Long courseId) {
        if (isFileMaterialExistToThisCourse(courseId)) {
            return this.servicesFactory.getMaterialService()
                    .findFileMaterialWithoutFileByCourseId(buildFileMaterials(courseId));
        }
        return new HashSet<>();
    }

    private Set<TextMaterials> findExistTextMaterialWithoutText(Long courseId) {
        if (isTextMaterialExistToThisCourse(courseId)) {
            return this.servicesFactory.getMaterialService()
                    .findTextMaterialWithoutTextByCourseId(buildTextMaterials(courseId));
        }
        return new HashSet<>();
    }

    private Boolean isFileMaterialExistToThisCourse(Long courseId) {
        return this.servicesFactory.getMaterialService()
                .isFileMaterialExistByCourseId(buildFileMaterials(courseId));

    }

    private Boolean isTextMaterialExistToThisCourse(Long courseId) {
        return this.servicesFactory.getMaterialService()
                .isTextMaterialExistByCourseId(buildTextMaterials(courseId));
    }

    private FileMaterials buildFileMaterials(Long courseId) {
        FileMaterials fileMaterials = this.entitiesFactory.getFileMaterials();
        fileMaterials.setCourse(this.entitiesFactory.getCourses());
        fileMaterials.getCourse().setCourseID(courseId);

        return fileMaterials;
    }

    private TextMaterials buildTextMaterials(Long courseId) {
        TextMaterials textMaterials = this.entitiesFactory.getTextMaterials();
        textMaterials.setCourse(this.entitiesFactory.getCourses());
        textMaterials.getCourse().setCourseID(courseId);

        return textMaterials;
    }
}
