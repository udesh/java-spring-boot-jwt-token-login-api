package com.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.entity.LoginHistory;

@Service
public interface LoginHistoryService {
	
	public LoginHistory save(String username, String attemptStatus);
	
	public LoginHistory save(LoginHistory entity);
	
	public List<LoginHistory> findByUsername(String username);

}
