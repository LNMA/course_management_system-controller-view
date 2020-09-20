package com.louay.controller.config;

import com.louay.model.entity.users.constant.Role;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requiresChannel(channel -> channel
                        .anyRequest().requiresSecure())
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringAntMatchers("/user/update/{email}/profile_picture-update",
                                "/course/{courseId}/feedback/add_file_post",
                                "/course/{courseId}/feedback/add_file-text_post",
                                "/course/{courseId}/feedback/{feedbackId}/edit-feedback/update_file-text_post",
                                "/course/{courseId}/feedback/{feedbackId}/edit-feedback/update_file_post",
                                "/course/{courseId}/update_course/course_picture",
                                "/course/{courseId}/material/add_file_material"))
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/student/**")
                        .access("@securityConfig.hasStudentRole(request)")
                        .antMatchers("/instructor/**")
                        .access("@securityConfig.hasInstructorRole(request)")
                        .antMatchers("/admin/**")
                        .access("@securityConfig.hasAdminRole(request)")
                        .antMatchers("/course/**", "/course_search/**", "/search/**", "/notification/**",
                                "/logout/**", "/session_id", "/user_verify/**", "/review/**", "/member/**", "/session_object/**",
                                "/user/**")
                        .access("@securityConfig.hasStudentRole(request) or @securityConfig.hasInstructorRole(request) or @securityConfig.hasAdminRole(request)")
                        .antMatchers("/student_sign_up/**", "/login/**", "/error/**", "/static/**", "/user_verify/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout/logout-account/{email}")
                        .logoutSuccessUrl("/login")
                        .clearAuthentication(true))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/error"))
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                        .expiredUrl("/error"));
    }

    public boolean hasAdminRole(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        Role role = (Role) session.getAttribute("role");
        if (role == null) {
            return false;
        }
        return role.compareTo(Role.ADMIN) == 0;
    }

    public boolean hasInstructorRole(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        Role role = (Role) session.getAttribute("role");
        if (role == null) {
            return false;
        }
        return role.compareTo(Role.INSTRUCTOR) == 0;
    }

    public boolean hasStudentRole(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        Role role = (Role) session.getAttribute("role");
        if (role == null) {
            return false;
        }
        return role.compareTo(Role.STUDENT) == 0;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }
}
