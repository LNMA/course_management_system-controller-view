# course_management_system-controller-view
controller-view component of course management system.

## Component
-This repository represent the controller-view component, you can found modal component at repository link below:
>https://github.com/LNMA/course_management_system-model

## Deploy

1. Before deploy controller-view component you must finish deploy modal component.
2. Make sure you have [JDK 11](https://www.oracle.com/java/technologies/javase-downloads.html) && [tomcat v9.0.37](https://archive.apache.org/dist/tomcat/tomcat-9/)
3. Edit all TODO comment inside the following path: `/src/main/java/com/louay/controller` .
4. add to your Run/Debug configuration `com.louay.controller.config.MySpringBootMVCApplication` class.
5. Check project Artifacts is Ok: Project must have one Exploded web application "war" and one Archive web application "war".
>**Preferably** remove them then build project using maven and again create the previous `war files`.
6. Mapping spring configuration classes.
>In intellij you can find it at project `structure/facets/spring/add` then add it all, also make sure there is no duplicates.
7. Run MySpringBootMVCApplication.class.
8. Past `https://localhost:8443/login` at browser address bar.

## About
>What is course management system ?

course ms is graduate project from `Software engineer trainning program at: Engineers Training Center / Jordan Engineers Association`.
I am [Louay Amr](https://www.linkedin.com/in/louay-amr-0b064b141) who is the author and the designer of project.

>What is the idea of the project?
1. course ms provide several authentication and authorization methods. 
2. sign up pages to student and instructor and verify user id by sending verification email.
3. login page tracking the user via cookies and session and url.
4. bootstrap 4 to order the page css.
5. security filters using spring security, csrf protection, roles for each user using spring security and view layer.
6. REST API using spring web-mvc and angularjs 1.8. 
7. json binding and converter using jackson.
8. upload images and pdf files using commons-fileupload library.
9. encrypted password using Argon2 Algorithm.
10. secure channel using JSSE-https-TLSv1.3.
11. build xls file using org.apache.poi.
12. jpa-hibernate was used to build modal component of the project.
13. mysql 8 using as database.
14. spring orm side by side with hibernate.
15. spring Ioc used at level dao and service also controllers. 

