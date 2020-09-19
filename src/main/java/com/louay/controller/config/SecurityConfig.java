package com.louay.controller.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.http.HttpServletRequest;

@EnableWebSecurity
@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requiresChannel().anyRequest().requiresSecure()
                .and()
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringAntMatchers("/user/update/{email}/profile_picture-update",
                                "/course/{courseId}/feedback/add_file_post",
                                "/course/{courseId}/feedback/add_file-text_post",
                                "/course/{courseId}/feedback/{feedbackId}/edit-feedback/update_file-text_post",
                                "/course/{courseId}/feedback/{feedbackId}/edit-feedback/update_file_post",
                                "/course/{courseId}/update_course/course_picture",
                                "/course/{courseId}/material/add_file_material"))
                .authorizeRequests(authorize  -> authorize
                        .antMatchers("/student/**")
                        .access("@sessionObjectController.hasStudentRole(request)")
                        .antMatchers("/instructor/**")
                        .access("@sessionObjectController.hasInstructorRole(request)")
                        .antMatchers("/course/**", "/course_search/**", "/search/**", "/notification/**",
                                "/logout/**", "/session_id", "/user_verify/**", "/review/**", "/member/**","/session_object/**",
                                "/user/**")
                        .access("@sessionObjectController.hasStudentRole(request) or @sessionObjectController.hasInstructorRole(request)")
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

   /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.inMemoryAuthentication()
                .withUser("student").password("studentPassword")
                .roles("STUDENT")
                .and()
                .withUser("instructor").password("instructorPassword")
                .roles("INSTRUCTOR")
                .and()
                .withUser("admin").password("adminPassword")
                .roles("ADMIN");
    }*/

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }
}
