package com.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.User;
import com.demo.exception.ResourceNotFoundException;
import com.demo.model.LoginHistoryDTO;
import com.demo.model.UserDTO;
import com.demo.service.LoginHistoryService;
import com.demo.service.UserAccountService;
import com.demo.utils.LoginHistoryConverter;
import com.demo.utils.ProjectUtils;
import com.demo.utils.UserConverter;

/**
 * The type User controller.
 *
 */
@RestController
@RequestMapping(ProjectUtils.API_URL)
public class UserLoginController {

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private LoginHistoryService loginHistoryService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private LoginHistoryConverter loginHistoryConverter;

	/**
	 * Register user.
	 *
	 * @param user
	 * @return the user
	 */
	@PostMapping(ProjectUtils.REGISTER_USER_URL)
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
		userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		User user = userAccountService.save(userConverter.convertFromDto(userDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(userConverter.convertFromEntity(user));
	}

	/**
	 * Get login history by username
	 *
	 * @param username
	 * @return login history
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/login-history/{username}")
	public ResponseEntity<List<LoginHistoryDTO>> getLoginHistoryByUser(
			@PathVariable(value = "username") String username) throws ResourceNotFoundException {
		return ResponseEntity.status(HttpStatus.OK)
				.body(loginHistoryConverter.createFromEntities(loginHistoryService.findByUsername(username)));
	}

	@Bean
	public LoginHistoryConverter loginHistoryConverter() {
		return new LoginHistoryConverter();
	}

	@Bean
	public UserConverter userConverter() {
		return new UserConverter();
	}
}
