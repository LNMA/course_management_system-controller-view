package com.louay.controller.config;

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
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
import org.springframework.web.context.WebApplicationContext;

@EnableWebSecurity
@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requiresChannel().anyRequest().requiresSecure()
                .and()
                .authorizeRequests()
                .antMatchers("/student/**", "/course/**", "/course_search/**", "/search/**",
                        "/logout/**", "/session_id", "/user_verify/**", "/review/**").anonymous()
                .antMatchers("/student_sign_up", "/login").permitAll()
                .mvcMatchers("/course/**", "/course_search/**", "/search/**",
                        "/logout/**", "/session_id", "/user_verify/**", "/student/**", "/review/**" ).authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .loginProcessingUrl("/login/perform_login").defaultSuccessUrl("/login/redirect_success_login")
                .loginProcessingUrl("/login/perform_session_login").defaultSuccessUrl("/login/redirect_tracer_success_login")
                .loginProcessingUrl("/login/perform_cookie_login").defaultSuccessUrl("/login/redirect_tracer_success_login")
                .and()
                .logout().logoutUrl("/logout/logout-account/{email}").clearAuthentication(true)
                .logoutSuccessUrl("/login")
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers("/student/student_home/{{email}}/profile_picture-update",
                        "/course/{courseId}/feedback/add_file_post",
                        "/course/{courseId}/feedback/add_file-text_post",
                        "/course/{courseId}/feedback/{feedbackId}/edit-feedback/update_file-text_post",
                        "/course/{courseId}/feedback/{feedbackId}/edit-feedback/update_file_post")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredSessionStrategy(new SimpleRedirectSessionInformationExpiredStrategy("/login"));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.inMemoryAuthentication()
                .withUser("student").password(passwordEncoder().encode("studentPass"))
                .roles("STUDENT")
                .and()
                .withUser("instructor").password(passwordEncoder().encode("instructorPss"))
                .roles("INSTRUCTOR")
                .and()
                .withUser("admin").password(passwordEncoder().encode("adminPass"))
                .roles("ADMIN");
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION)
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }
}
