package com.louay.controller.util.filter;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EmailFilter {

    public String filterEmailUrlToOriginal(String urlEmail) {
        int emailLength = urlEmail.length();
        String subEmail = urlEmail.substring(emailLength - 4, emailLength);
        if (subEmail.equals("-com")) {
            return urlEmail.substring(0, emailLength - 4) + ".com";
        }
        return urlEmail;
    }

    public String filterOriginalToEmailUrl(String originalEmail) {
        if (originalEmail == null) {
            return "";
        }
        int emailLength = originalEmail.length();
        String subEmail = originalEmail.substring(emailLength - 4, emailLength);
        if (subEmail.equals(".com")) {
            return originalEmail.substring(0, emailLength - 4) + "-com";
        }
        return originalEmail;
    }

}
