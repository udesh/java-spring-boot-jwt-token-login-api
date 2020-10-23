package com.demo.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.demo.entity.LoginHistory;
import com.demo.model.UserDTO;
import com.demo.service.LoginHistoryService;
import com.demo.utils.ProjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public static final String SECRET = "FG^723fhhQW12~123qwert123#@$fr!67DSWa";
	public static final long EXPIRATION_TIME = 100000; 
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";

	private AuthenticationManager authenticationManager;
	
	private LoginHistoryService loginHistoryService;
	
	private LoginHistory loginHistory;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
			LoginHistoryService loginHistoryService) {
		this.authenticationManager = authenticationManager;
		this.loginHistoryService = loginHistoryService;
	}
	

	public LoginHistory getLoginHistory() {
		return loginHistory;
	}


	public void setLoginHistory(LoginHistory loginHistory) {
		this.loginHistory = loginHistory;
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {

			UserDTO loginUser = new ObjectMapper().readValue(request.getInputStream(),
					UserDTO.class);
			this.setLoginHistory(loginHistoryService.save(loginUser.getUsername(),ProjectUtils.PENDING_STATUS));
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(),
					loginUser.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String token = JWT.create().withSubject(((User) authResult.getPrincipal()).getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // JWT token validity time
				.sign(Algorithm.HMAC512(SECRET.getBytes())); // JWT Signature
		this.getLoginHistory().setAttemptStatus(ProjectUtils.SUCCESS_STATUS);
		loginHistoryService.save(this.getLoginHistory());
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}
    
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		this.getLoginHistory().setAttemptStatus(ProjectUtils.FAIL_STATUS);
		loginHistoryService.save(this.getLoginHistory());
		super.unsuccessfulAuthentication(request, response, failed);

	}
}
