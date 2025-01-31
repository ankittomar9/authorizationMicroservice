package com.company.authorizationMicroservice.config;
import com.company.authorizationMicroservice.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration  extends  WebSecurityAdapter{

        @Autowired
        CustomUserDetailsService customUserDetailsService;

        private JWTA

}
