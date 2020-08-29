package com.louay.controller.material;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.controller.factory.WrappersFactory;
import com.louay.model.entity.material.FileMaterials;
import com.louay.model.entity.material.MaterialContent;
import com.louay.model.entity.material.TextMaterials;
import com.louay.model.entity.material.constant.MaterialType;
import com.louay.model.entity.wrapper.FileMaterialWithOutFile;
import com.louay.model.entity.wrapper.MaterialWithOutContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/course/{courseId}/material")
public class MaterialCourseHomeController implements Serializable {
    private static final long serialVersionUID = -8439626380078593471L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public MaterialCourseHomeController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        if (entitiesFactory == null || servicesFactory == null) {
            throw new IllegalArgumentException("factory cannot be null at MaterialCourseController.class");
        }
        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @GetMapping
    public String viewCourseMaterialHomePage() {
        return "/static/html/course_material.html";
    }

    @GetMapping(value = "/{materialType}/{materialId}/get_content", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public MaterialContent getCourseMaterialWithContent(@PathVariable(name = "materialId") String materialId,
                                                        @PathVariable(name = "materialType") String materialType) {
        long materialIdNumber = 0L;
        if (materialId != null) {
            materialIdNumber = Long.parseLong(materialId);
        }

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
        long courseIdNumber = 0L;
        if (courseId != null) {
            courseIdNumber = Long.parseLong(courseId);
        }
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
