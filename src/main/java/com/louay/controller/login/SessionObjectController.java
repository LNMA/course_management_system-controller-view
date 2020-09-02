package com.louay.controller.login;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.io.Serializable;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
public class SessionObjectController implements Serializable {

    private static final long serialVersionUID = 1137743040312075832L;

    @GetMapping(value = "/session_id", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getSessionId(@SessionAttribute(value = "id", required = false) String sessionEmail){
        return sessionEmail;
    }
}
