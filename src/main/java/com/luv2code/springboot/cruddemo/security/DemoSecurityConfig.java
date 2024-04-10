package com.luv2code.springboot.cruddemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {


    // setting-up users for JDBC (custom tables, for standard tables see below the code)
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // query for getting the user from my table (Spring will input the username instead of '?')
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT user_id, pw, active FROM members WHERE user_id=?");

        // query for getting the roles from my table (Spring will input the username instead of '?')
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT user_id, role FROM roles WHERE user_id=?");

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // setting-up access to HTTP methods on endpoints to different roles

        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "api/employees").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "api/employees/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "api/employees/**").hasRole("ADMIN")
        );

        // use HTTP Basic authentication
        http.httpBasic(Customizer.withDefaults());

        // disable Cross-site Reference Forgery (CSRF)
        // in general not required for stateless REST API that uses POST, PUT, DELETE and/or PATCH
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}


    /*    // setting-up users for JDBC (standard tables 'users' + 'authorities'... no hardcoding (preffered way)
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }*/

    /*    // setting-up users of my app by hardcoding (not preffered)
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails john = User.builder()
                .username("john")
                .password("{noop}testJohn123")
                .roles("EMPLOYEE")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}testMary123")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}testSusan123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(john, mary, susan);
    }*/
