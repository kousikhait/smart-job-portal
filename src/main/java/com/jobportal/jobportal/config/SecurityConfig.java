//package com.jobportal.jobportal.config;
//
//import org.springframework.context.annotation.*;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import com.jobportal.jobportal.security.CustomLoginSuccessHandler;
//
//@Configuration
//public class SecurityConfig {
//	
////	  private final CustomLoginSuccessHandler successHandler;
////
////	    public SecurityConfig(CustomLoginSuccessHandler successHandler) {
////	        this.successHandler = successHandler;
////	    }
////
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////
////        http
////            .csrf(csrf->csrf.disable()).authorizeHttpRequests(auth->auth
////                .requestMatchers("/", "/login", "/css/**").permitAll()
////                .requestMatchers("/recruiter/**").hasRole("RECRUITER")
////                .anyRequest().authenticated()
////            )
////            .formLogin(form -> form
////                .loginPage("/login")
////                .successHandler(successHandler).permitAll()
////            )
////            .logout(logout -> logout.permitAll());
////
////        return http.build();
////    }
////    
//////    @Bean
//////    public PasswordEncoder passwordEncoder() {
//////        return new BCryptPasswordEncoder();
//////    }
////    
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return NoOpPasswordEncoder.getInstance();
////    }
//    
//  
//    
//}