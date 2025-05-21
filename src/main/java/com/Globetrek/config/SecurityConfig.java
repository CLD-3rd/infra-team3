package com.Globetrek.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.Globetrek.service.PrincipalDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final PrincipalDetailsService principalDetailsService;

	 	@Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .authorizeHttpRequests(authz -> authz
 		.requestMatchers("/auth/**", "/search/**", "/countries/**", "/gallery/**" ).permitAll()   // 이 경로는 로그인 없이도 접근 가능
	            		 .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()  // 정적 리소스 허용
	            		 .anyRequest().authenticated()  // 그 외 경로는 로그인 필요
	            //		.anyRequest().permitAll() // 모든 요청 허용 (임시)

	            )
	            .formLogin((form) -> form
	                .loginPage("/auth/login")
	                .failureUrl("/auth/login?error")
	                .permitAll()
	            )
	            .userDetailsService(principalDetailsService)
	            .logout((logout) -> logout.permitAll());

	        return http.build();
	    }
	 	// 암호화 
	 	@Bean
	 	public PasswordEncoder passwordEncoder() {
	 	    return new BCryptPasswordEncoder();
	 	}

	    
}
