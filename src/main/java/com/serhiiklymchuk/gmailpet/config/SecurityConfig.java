package com.serhiiklymchuk.gmailpet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(@Qualifier("userServiceImpl") UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, BCryptPasswordEncoder bCryptPasswordEncoder1) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder1;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

         http
                 .authorizeRequests()
                 .antMatchers( "/user/**" ,"/", "/js/**", "/css/**", "/images/**").permitAll()
                .anyRequest().authenticated()
                .and()
                 .formLogin()
                .loginPage("/login")
                 .defaultSuccessUrl("/user/home", true)
                 .permitAll()
                .and()
                 .logout()
                 .permitAll()
                .and()
                 .csrf();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

}
