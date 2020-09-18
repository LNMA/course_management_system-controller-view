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
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requiresChannel().anyRequest().requiresSecure()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers("/user/update/{{email}}/profile_picture-update",
                        "/course/{courseId}/feedback/add_file_post",
                        "/course/{courseId}/feedback/add_file-text_post",
                        "/course/{courseId}/feedback/{feedbackId}/edit-feedback/update_file-text_post",
                        "/course/{courseId}/feedback/{feedbackId}/edit-feedback/update_file_post",
                        "/course/{courseId}/update_course/course_picture")
                .and()
                .authorizeRequests()
                .antMatchers("/student/**").anonymous()
                .antMatchers("/instructor/**").anonymous()
                .antMatchers("/course/**", "/course_search/**", "/search/**", "/notification/**",
                        "/logout/**", "/session_id", "/user_verify/**", "/review/**", "/member/**",
                        "/user/**").anonymous()
                .antMatchers("/student_sign_up/**", "/login/**", "/error/**", "/static/**",
                        "/user_verify/**", "/session_object/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .permitAll()
                .and()
                .logout().logoutUrl("/logout/logout-account/{email}").clearAuthentication(true)
                .and()
                .exceptionHandling().accessDeniedPage("/error")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).maximumSessions(1)
                .maxSessionsPreventsLogin(true).expiredUrl("/error");
    }

    @Override
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
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }
}
