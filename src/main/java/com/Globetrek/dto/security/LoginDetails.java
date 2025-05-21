package com.Globetrek.dto.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Globetrek.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginDetails implements UserDetails{
	
	
	    private final User user;
	    
	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return Collections.emptyList();
	    }

	    @Override
	    public String getPassword() {
	        return user.getPassword();  
	    }

	    @Override
	    public String getUsername() {
	        return user.getUserName();  
	    }
	    
	    public String getNickname() {
	        return user.getNickname(); 
	    }

}
