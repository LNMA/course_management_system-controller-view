# course_management_system-controller-view
controller-view component of course management system.

## Component
-This repository represent the controller-view component, you can found modal component at repository link below:
>https://github.com/LNMA/course_management_system-model

## Deploy

1. Before deploy controller-view component you must finish deploy [modal component](https://github.com/LNMA/course_management_system-model).
2. Make sure you have [JDK 11](https://www.oracle.com/java/technologies/javase-downloads.html) && [tomcat v9.0.37](https://archive.apache.org/dist/tomcat/tomcat-9/)
3. Edit all TODO comment inside the following path: `/src/main/java/com/louay/controller` .
4. add to your Run/Debug configuration `com.louay.controller.config.MySpringBootMVCApplication` class.
5. Check project Artifacts is Ok: Project must have one Exploded web application "war" and one Archive web application "war".
>**Preferably** remove them then build project using maven and again create the previous `war files`.
6. Mapping spring configuration classes.
>In intellij you can find it at project `structure/facets/spring/add` then add it all, also make sure there are no duplicates.
7. Run MySpringBootMVCApplication.class.
8. Past `https://localhost:8443/login` at browser address bar.

## About
>What is course management system ?

course ms is graduate project from `Software engineer training program at: Engineers Training Center / Jordan Engineers Association`.
I am [Louay Amr](https://www.linkedin.com/in/louay-amr-0b064b141) who is the author, and the designer of project.

>What is the idea of the project?
1. course ms provide several authentications and authorization methods. 
2. sign up pages to student and instructor and verify user id by sending verification email.
3. login page tracking the user via cookies and session and url.
4. bootstrap 4 to order the page css.
5. security filters using spring security, csrf protection, roles for each user using spring security and view layer.
6. REST API using spring web-mvc and angularjs 1.8. 
7. json binding and converter using jackson.
8. upload images and pdf files using commons-fileupload library.
9. an encrypted password using Argon2 Algorithm.
10. secure channel using JSSE-https-TLSv1.3.
11. build xls file using org.apache.poi.
12. jpa-hibernate was used to build modal component of the project.
13. mysql 8 uses as database.
14. spring orm side by side with hibernate.
15. spring Ioc used at level dao and service also controllers. 

>What are the features the project provides?

1. login and sign up pages with suitable validation.
2. home pages for each user according to its permission.
3. search service, can search for user, course, material, feedback.
4. edit and view user profile.
5. search for course only.
6. review page to account and course.
7. student join service with suitable validation.
8. logout service.
9. course home page.
10. instructor role can edit its course info.
11. course search service can search at course material, feedback.
12. exit from course, and change user status.
13. user status can see at account review page online/offline, at course/out course.
14. user notification for new material, feedback.
15. feedback for each course, can post text/image/text-image post.
16. comment at feedback service, can user write comment for each post.
17. edit/delete feedback service.
18. material service for each course, can users view/add/edit material according to its role.
19. material can be link, image, pdf files.
20. ajax often used to submit form, that mean reloads page neglected. 
21. admin only can register instructor users, export attendances student sheet.
23. after user finish sign up process email will be sent at user email to valid user account.
24. show user joined courses at home page.
25. review account show user info plus status plus joined courses list.
26. review course page show course info plus member list plus join to course service.
27. add course service provide to instructor role.
28. security present at all level.

