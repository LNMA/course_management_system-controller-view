package com.louay.controller.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http2.Http2Protocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.CssLinkResourceTransformer;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.louay.controller"})
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/static/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                .resourceChain(true)
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
                .addTransformer(new CssLinkResourceTransformer());
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public ConfigurableServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(getHttpConnector());
        return tomcat;
    }

    private Connector getHttpConnector() {
       /*SSLHostConfig sslHostConfig = new SSLHostConfig();
        sslHostConfig.setEnabledCiphers(new String[]{"ECDHE-ECDSA-AES128-GCM-SHA256","ECDHE-RSA-AES128-GCM-SHA256",
                "ECDHE-ECDSA-AES256-GCM-SHA384","ECDHE-RSA-AES256-GCM-SHA384","ECDHE-ECDSA-CHACHA20-POLY1305",
                "ECDHE-RSA-CHACHA20-POLY1305","DHE-RSA-AES128-GCM-SHA256","DHE-RSA-AES256-GCM-SHA384"});
        sslHostConfig.setEnabledProtocols(new String[]{"TLSv1","TLSv1.1","TLSv1.2","TLSv1.3"});
        sslHostConfig.setSslProtocol("TLSv1.3");
        sslHostConfig.setCaCertificateFile("tomcat.pem");
        sslHostConfig.setCertificateChainFile("tomcat.pem");
        sslHostConfig.setHonorCipherOrder(false);
        sslHostConfig.setDisableSessionTickets(true);
        sslHostConfig.setCertificateKeyFile("keystore.p12");
        sslHostConfig.setCertificateKeyPassword("123456789@tomcat");
        sslHostConfig.setCertificateKeyAlias("tomcat");
        sslHostConfig.setCertificateKeystoreFile("keystore.p12");
        sslHostConfig.setCertificateKeystorePassword("123456789@tomcat");
        sslHostConfig.setCertificateKeystoreType("pkcs12");*/

        Connector connector = new Connector("org.apache.coyote.http11.Http11Nio2Protocol");
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setRedirectPort(8443);
        connector.setSecure(false);
        connector.addUpgradeProtocol(new Http2Protocol());
        return connector;
    }
}
