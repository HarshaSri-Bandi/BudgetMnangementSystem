package com.example.UserService.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter;

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Bean
    UserDetailsService userDetailsService() {
        return new CustomerDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity security) throws Exception {

        security.csrf(configurer->configurer.disable())
                .authorizeHttpRequests(req->req.requestMatchers("/login", "/addUser", "/getUserByMail/{email}",
                        "/getUserByContactNumber/**", "/getUserInfo/{email}", "/postNewIncomeById/**","/PostNewExpenseForCustomer/{customerId}",
                        "/updateUserData/{fullName}/{email}/{contactNumber}", "/updatePasswordByEmail/{email}/{password}",
                        "/api/getUserByName/{name}","/actuator/**", "/v3/api-docs/**",
                         "/actuator","/swagger-ui/**", "/swagger-resources/**", "/swagger-resources").permitAll().anyRequest().authenticated())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter,UsernamePasswordAuthenticationFilter.class);
//        security.csrf().disable().authorizeHttpRequests()
//                .requestMatchers("/login", "/addUser", "/getUserByMail/{email}", "/addSeller",
//                        "/getUserByContactNumber/**", "/getUserInfo/{email}",
//                        "/updateUserData/{fullName}/{email}/{contactNumber}",
//                        "/updatePasswordByEmail/{email}/{password}", "/api/getUserByName/{name}", "/actuator/**",
//                        "/actuator")
//                .permitAll().anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(entryPoint)
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return security.build();
    }
}