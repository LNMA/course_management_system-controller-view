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
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;

@EnableWebSecurity
@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requiresChannel().anyRequest().requiresSecure()
                .and()
                .authorizeRequests()
                .antMatchers("/student_sign_up").permitAll()
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/perform_login")
                .and()
                .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(60 * 60 * 24 * 30)
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringAntMatchers("/student/student_home/{{email}}/profile_picture-update")
                .and()
                .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).maximumSessions(1);
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
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }
}
