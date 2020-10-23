package com.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.demo.entity.User;

@Service
public interface UserAccountService extends UserDetailsService {
	
	public User save(User user);

}
