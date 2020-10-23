package com.demo.utils;

import com.demo.entity.LoginHistory;
import com.demo.model.LoginHistoryDTO;

public class LoginHistoryConverter extends Converter<LoginHistoryDTO, LoginHistory> {
	
	public LoginHistoryConverter() {
		super(LoginHistoryConverter::convertToEntity, LoginHistoryConverter::convertToDto);
	}
	
	private static LoginHistoryDTO convertToDto(LoginHistory loginHistory) {		
		return new LoginHistoryDTO(loginHistory.getId(), loginHistory.getUsername(), loginHistory.getAttemptStatus(),
				loginHistory.getAttemptAt());
	}

	private static LoginHistory convertToEntity(LoginHistoryDTO loginHistoryDTO) {
		return new LoginHistory(loginHistoryDTO.getId(), loginHistoryDTO.getUsername(), loginHistoryDTO.getAttemptStatus(),
				loginHistoryDTO.getAttemptAt());
	}
	

}
