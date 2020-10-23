package com.demo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.demo.entity.LoginHistory;
import com.demo.repository.LoginHistoryRepository;
import com.demo.service.LoginHistoryService;

@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {
	
	@Autowired
	private LoginHistoryRepository loginHistoryRepository;

	@Override
	public LoginHistory save(String username, String attemptStatus) {
		LoginHistory entity = new LoginHistory();
		entity.setUsername(username);
		entity.setAttemptStatus(attemptStatus);
		entity.setAttemptAt(new Date());
		return loginHistoryRepository.saveAndFlush(entity);
	}

	@Override
	public List<LoginHistory> findByUsername(String username) {
		Pageable pageable = PageRequest.of(0, 5, Sort.by(Direction.DESC, "attemptAt"));
		return loginHistoryRepository.findByUsername(username, pageable);
	}

	@Override
	public LoginHistory save(LoginHistory entity) {
		return loginHistoryRepository.saveAndFlush(entity);
	}

}
