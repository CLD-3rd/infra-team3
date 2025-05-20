package com.Globetrek.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Globetrek.dto.security.LoginDetails;
import com.Globetrek.entity.User;
import com.Globetrek.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(userName);
		
		if(user == null) {
	        throw new UsernameNotFoundException("User not found");
	    }
		// user를 제대로 찾는지 확인하기 위한 출력
		System.out.println("DB에서 찾은 사용자: " + user.getUserName());
		if(user != null) {
			return new LoginDetails(user);
		}
		return null;
	}
}
