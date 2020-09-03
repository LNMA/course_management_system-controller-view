package com.louay.controller.search;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.controller.factory.WrappersFactory;
import com.louay.model.entity.courses.Courses;
import com.louay.model.entity.feedback.CourseFeedback;
import com.louay.model.entity.material.CourseMaterials;
import com.louay.model.entity.users.Users;
import com.louay.model.entity.wrapper.GeneralSearch;
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
@RequestMapping(value = "/search/{key}")
public class GeneralSearchController implements Serializable {
    private static final long serialVersionUID = 5049902782600651810L;
    private final ServicesFactory servicesFactory;
    private final EntitiesFactory entitiesFactory;
    private final WrappersFactory wrappersFactory;

    @Autowired
    public GeneralSearchController(ServicesFactory servicesFactory, EntitiesFactory entitiesFactory,
                                   WrappersFactory wrappersFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");
        Assert.notNull(wrappersFactory, "wrappersFactory cannot be null!.");

        this.servicesFactory = servicesFactory;
        this.entitiesFactory = entitiesFactory;
        this.wrappersFactory = wrappersFactory;
    }


    @GetMapping
    public String viewsSearchPage(){
        return "/static/html/general_search.html";
    }

    @GetMapping(value = "/{pageNumber}/get_result", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<Object> getSearchedResult(@PathVariable(value = "key") String key,
                                         @PathVariable(value = "pageNumber") String pageNumber) {
        Assert.notNull(key, "search key cannot be null!.");
        Assert.notNull(pageNumber, "pageNumber key cannot be null!.");

        int pageNumberParse = Integer.parseInt(pageNumber);
        int pageSize = 9;

        return assemblySetResult(buildGeneralSearch(key, pageNumberParse, pageSize));
    }

    private Set<Object> assemblySetResult(GeneralSearch generalSearch){
        Set<Users> accountsSet = this.servicesFactory.getAccountService()
                .findUserLikeForSearch(generalSearch);

        Set<Courses>  coursesSet = this.servicesFactory.getCourseService()
                .findCourseLikeForSearch(generalSearch);

        Set<CourseFeedback> courseFeedbacks = this.servicesFactory
                .getFeedbackService().findCourseFeedbackLikeForSearch(generalSearch);

        Set<CourseMaterials> courseMaterialsSet = this.servicesFactory.getMaterialService()
                .findCourseMaterialsLikeForSearch(generalSearch);

        Set<Object> objectSet = new HashSet<>();
        if (!accountsSet.isEmpty()){
            objectSet.addAll(accountsSet);
        }
        if (!coursesSet.isEmpty()){
            objectSet.addAll(coursesSet);
        }
        if (!courseFeedbacks.isEmpty()){
            objectSet.addAll(courseFeedbacks);
        }
        if (!courseMaterialsSet.isEmpty()){
            objectSet.addAll(courseMaterialsSet);
        }

        return objectSet;
    }

    private GeneralSearch buildGeneralSearch(String key, int pageNumber, int pageSize) {
        GeneralSearch generalSearch = this.wrappersFactory.getGeneralSearch();
        generalSearch.setKey(key);
        generalSearch.setPageNumber(pageNumber);
        generalSearch.setPageSize(pageSize);

        return generalSearch;
    }

    @GetMapping(value = "/get_record_number", produces = MediaType.ALL_VALUE)
    @ResponseBody
    public Long getSearchedRecordNumber(@PathVariable(value = "key") String key) {
        Assert.notNull(key, "search key cannot be null!.");
        return assemblyRecordNumber(buildGeneralSearch(key));
    }

    private Long assemblyRecordNumber(GeneralSearch generalSearch) {
        Long accountResult = this.servicesFactory.getAccountService().getCountUserLikeForSearch(generalSearch);
        Long courseResult = this.servicesFactory.getCourseService().getCountCourseLikeForSearch(generalSearch);
        Long feedbackResult = this.servicesFactory.getFeedbackService().getCountCourseFeedbackLikeForSearch(generalSearch);
        Long materialResult = this.servicesFactory.getMaterialService().getCountCourseMaterialsLikeForSearch(generalSearch);

        return accountResult + courseResult + feedbackResult + materialResult;
    }

    private GeneralSearch buildGeneralSearch(String key) {
        GeneralSearch generalSearch = this.wrappersFactory.getGeneralSearch();
        generalSearch.setKey(key);

        return generalSearch;
    }
}
