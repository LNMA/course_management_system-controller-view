package com.louay.controller.material;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.model.entity.material.CourseMaterials;
import com.louay.model.entity.material.FileMaterials;
import com.louay.model.entity.material.TextMaterials;
import com.louay.model.entity.material.constant.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping(value = "/course/{courseId}/material")
public class CreateEditMaterialController implements Serializable {
    private static final long serialVersionUID = -614034491981897043L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    @Autowired
    public CreateEditMaterialController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @RequestMapping(value = "/{materialId}/remove_material", produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String removeMaterial(@PathVariable(value = "materialId", required = false) String materialIdInPath,
                                 @PathVariable(value = "courseId", required = false) String courseIdInPath) {
        Assert.notNull(materialIdInPath, "material id cannot be null!.");
        Assert.notNull(courseIdInPath, "course id cannot be null!.");

        Long materialId = Long.valueOf(materialIdInPath);
        deleteMaterial(materialId);

        return String.format("/course/%s/material", courseIdInPath);
    }

    private void deleteMaterial(Long materialId) {
        CourseMaterials courseMaterials = buildCourseMaterials(materialId);

        this.servicesFactory.getMaterialService().deleteCourseMaterialsByMaterialId(courseMaterials);
    }

    private CourseMaterials buildCourseMaterials(Long materialId) {
        CourseMaterials courseMaterials = this.entitiesFactory.getCourseMaterials();
        courseMaterials.setMaterialID(materialId);

        return courseMaterials;
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
        textMaterialsEntity = saveTextMaterials(textMaterialsEntity);

        return ResponseEntity.ok().body(String.format("Text material ID %d created successfully.",
                textMaterialsEntity.getMaterialID()));
    }

    @PostMapping(value = "/add_file_material", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
                        !Objects.requireNonNull(multipartFile.getContentType()).equals("application/pdf"))) {
            throw new IllegalStateException("It's seem there something wrong in while process this operation !.");
        }

        Long courseId = Long.valueOf(courseIdInPath);
        String contentType = multipartFile.getContentType();
        CourseMaterials courseMaterials = buildCourseMaterials(courseId, emailInSession);
        FileMaterials fileMaterials = null;
        try {
            if (contentType.contains("image")) {
                fileMaterials = buildFileMaterials(materialName, multipartFile.getBytes(), FileType.IMAGE);
            } else if (contentType.equals("application/pdf")) {
                fileMaterials = buildFileMaterials(materialName, multipartFile.getBytes(), FileType.PDF);
            } else {
                throw new UnsupportedOperationException("the must be image or pdf ONLY!.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Assert.notNull(fileMaterials, "fileMaterials cannot be null!.");
        FileMaterials fileMaterialsEntity = assembleFileMaterials(fileMaterials, courseMaterials);
        saveFileMaterials(fileMaterialsEntity);

        return String.format("redirect:/course/%s/material", courseIdInPath);
    }

    private TextMaterials saveTextMaterials(TextMaterials textMaterials) {
        return this.servicesFactory.getMaterialService().createTextMaterials(textMaterials);
    }

    private void saveFileMaterials(FileMaterials fileMaterials) {
        this.servicesFactory.getMaterialService().createFileMaterials(fileMaterials);
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
