//package com.jobportal.jobportal.service;
//
//import java.util.Collections;
//import java.util.List;
//
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.jobportal.jobportal.entity.User;
//import com.jobportal.jobportal.repository.UserRepository;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService{
//	
//	public final UserRepository userRepository;
//	
//
//	public CustomUserDetailsService(UserRepository userRepository) {
//		super();
//		this.userRepository = userRepository;
//	}
//
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User user = userRepository.findByEmail(username).orElseThrow(() ->
//                        new UsernameNotFoundException("User not found"));
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                List.of(new SimpleGrantedAuthority("ROLE_" +user.getRole().getName()))
//               
//        );
//        
//	}   
//	
//
//}
