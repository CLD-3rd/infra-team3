package com.Globetrek.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
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
	            .authorizeHttpRequests((authz) -> authz
	            		/*.antMatchers("/auth/login", "/auth/signup").permitAll()  // 이 경로는 모두 허용
        				  .anyRequest().authenticated()  // 그 외 요청은 인증 필요 */
	            		.anyRequest().permitAll() // 모든 요청 허용 (임시)
	                
	            )
	            .formLogin((form) -> form
	                .loginPage("/auth/login") 
	                .defaultSuccessUrl("/countries") // 로그인 성공 시 이동 경로, 나중에 수정
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
