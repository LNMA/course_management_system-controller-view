package com.louay.controller.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.apache.coyote.http2.Http2Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.Http2;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.CssLinkResourceTransformer;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.louay.controller"})
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    @Qualifier("mappingJackson")
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

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
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setOrder(1);

        return viewResolver;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(1024 * 1024 * 70);
        commonsMultipartResolver.setMaxUploadSizePerFile(1024 * 1024 * 70);
        try {
            FileUrlResource tempFolder = new FileUrlResource("tmp");
            commonsMultipartResolver.setUploadTempDir(tempFolder);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return commonsMultipartResolver;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
        converters.add(this.mappingJackson2HttpMessageConverter);
    }

    @Bean("mappingJackson")
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = converter.getObjectMapper();
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        hibernate5Module.enable(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS);
        mapper.registerModule(hibernate5Module);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        return converter;
    }

    @Bean
    public ConfigurableServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.setProtocol("org.apache.coyote.http11.Http11Nio2Protocol");
        Http2 http2 = new Http2();
        http2.setEnabled(true);
        tomcat.setHttp2(http2);
        tomcat.addErrorPages(new ErrorPage("/error"));
        tomcat.setPort(8080);
        tomcat.addAdditionalTomcatConnectors(getHttpConnector());
        return tomcat;
    }

    private Connector getHttpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11Nio2Protocol");
        Http2Protocol http2Protocol = new Http2Protocol();
        Http11Nio2Protocol protocol = (Http11Nio2Protocol) connector.getProtocolHandler();
        connector.setMaxPostSize(1024 * 1024 * 70);

        ClassPathResource keystoreResource = new ClassPathResource("jsse/keystore.jks");
        ClassPathResource truststoreResource = new ClassPathResource("jsse/cacerts.jks");
        File keystore = new File(keystoreResource.getPath());
        File truststore = new File(truststoreResource.getPath());
        connector.setScheme("https");
        connector.setSecure(true);
        connector.setPort(8443);
        protocol.setSslImplementationName("org.apache.tomcat.util.net.jsse.JSSEImplementation");
        protocol.addUpgradeProtocol(http2Protocol);
        protocol.setSSLEnabled(true);
        protocol.setKeystoreType("jks");
        protocol.setTruststoreType("jks");
        protocol.setKeystoreFile(keystore.getAbsolutePath());
        protocol.setKeystorePass("123456789@tomcat");
        protocol.setTruststoreFile(truststore.getAbsolutePath());
        protocol.setTruststorePass("123456789@tomcat");
        protocol.setKeyAlias("tomcat");
        protocol.setSslProtocol("TLS");
        protocol.setSslEnabledProtocols("TLSv1+TLSv1.1+TLSv1.2+TLSv1.3");
        protocol.setSSLCipherSuite("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384:TLS_DHE_RSA_WITH_AES_256_CBC_SHA256:TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256:TLS_DHE_RSA_WITH_AES_128_CBC_SHA256:TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384:TLS_DHE_RSA_WITH_AES_256_GCM_SHA384:TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256:TLS_DHE_RSA_WITH_AES_128_GCM_SHA256:TLS_AES_128_GCM_SHA256:TLS_AES_256_GCM_SHA384");
        protocol.setClientAuth("want");
        return connector;
    }
}
