package com.louay.controller.admin;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/admin/admin_home/{email:.+}")
public class AdminHomePageController implements Serializable {
    private static final long serialVersionUID = -3482104992145766184L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;

    public AdminHomePageController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
    }

    @GetMapping
    public String viewAdminHome(){
        return "/static/html/admin-home.html";
    }

}
