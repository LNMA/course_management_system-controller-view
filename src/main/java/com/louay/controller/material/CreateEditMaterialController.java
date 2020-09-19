package com.louay.controller.material;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.material.CourseMaterials;
import com.louay.model.entity.material.FileMaterials;
import com.louay.model.entity.material.TextMaterials;
import com.louay.model.entity.material.constant.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Objects;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/course/{courseId}/material")
public class CreateEditMaterialController implements Serializable {
    private static final long serialVersionUID = -5445081743525567521L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public CreateEditMaterialController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @PostMapping(value = "/add_text_material", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addTextMaterial(@RequestBody TextMaterials textMaterials,
                                                  @PathVariable(value = "courseId", required = false) String courseIdInPath,
                                                  @SessionAttribute(value = "id", required = false) String emailInSession) {
        Assert.notNull(textMaterials.getText(), "materialText cannot be null!.");
        Assert.notNull(textMaterials.getMaterialName(), "materialName cannot be null!.");
        Assert.notNull(courseIdInPath, "courseIdInPath cannot be null!.");
        Assert.notNull(emailInSession, "emailInSession cannot be null!.");

        Long courseId = Long.valueOf(courseIdInPath);
        CourseMaterials courseMaterials = buildCourseMaterials(courseId, emailInSession);
        TextMaterials textMaterialsEntity = assembleTextMaterial(textMaterials, courseMaterials);
        saveTextMaterials(textMaterialsEntity);

        return ResponseEntity.ok().body("Text material created successfully.");
    }

    @PostMapping(value = "/add_file_material", consumes = MediaType.MULTIPART_MIXED_VALUE)
    public String addFileMaterial(@RequestParam(value = "file") MultipartFile multipartFile,
                                  @RequestParam(value = "courseNameFileMaterial") String materialName,
                                  @PathVariable(value = "courseId", required = false) String courseIdInPath,
                                  @SessionAttribute(value = "id", required = false) String emailInSession) {
        Assert.notNull(multipartFile, "file cannot be null!.");
        Assert.notNull(materialName, "materialName cannot be null!.");
        Assert.notNull(courseIdInPath, "courseIdInPath cannot be null!.");
        Assert.notNull(emailInSession, "emailInSession cannot be null!.");

        if (multipartFile.isEmpty() || multipartFile.getSize() > (1024 * 1024 * 50) ||
                (!Objects.requireNonNull(multipartFile.getContentType()).contains("image") &&
                        !Objects.requireNonNull(multipartFile.getContentType()).contains("pdf"))) {
            throw new IllegalStateException("It's seem there something wrong in while process this operation !.");
        }

        System.out.println(multipartFile.getContentType());


        return String.format("redirect:/course/%s/material", courseIdInPath);
    }

    private void saveTextMaterials(TextMaterials textMaterials) {
        this.servicesFactory.getMaterialService().createTextMaterials(textMaterials);
    }

    private FileMaterials saveFileMaterials(FileMaterials fileMaterials) {
        return this.servicesFactory.getMaterialService().createFileMaterials(fileMaterials);
    }

    private TextMaterials assembleTextMaterial(TextMaterials textMaterials, CourseMaterials courseMaterials) {
        textMaterials.setUser(courseMaterials.getUser());
        textMaterials.setCourse(courseMaterials.getCourse());

        return textMaterials;
    }

    private FileMaterials assembleFileMaterials(FileMaterials fileMaterials, CourseMaterials courseMaterials) {
        fileMaterials.setCourse(courseMaterials.getCourse());
        fileMaterials.setUser(courseMaterials.getUser());

        return fileMaterials;
    }

    private FileMaterials buildFileMaterials(String materialName, byte[] file, FileType fileType) {
        FileMaterials fileMaterials = this.entitiesFactory.getFileMaterials();
        fileMaterials.setFile(file);
        fileMaterials.setFileType(fileType);
        fileMaterials.setMaterialName(materialName);

        return fileMaterials;
    }

    private CourseMaterials buildCourseMaterials(Long courseId, String email) {
        CourseMaterials courseMaterials = this.entitiesFactory.getCourseMaterials();
        courseMaterials.setCourse(this.entitiesFactory.getCourses());
        courseMaterials.getCourse().setCourseID(courseId);
        courseMaterials.setUser(this.entitiesFactory.getUsers());
        courseMaterials.getUser().setEmail(email);

        return courseMaterials;
    }

}
