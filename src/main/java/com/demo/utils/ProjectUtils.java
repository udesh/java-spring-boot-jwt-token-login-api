package com.demo.utils;

public class ProjectUtils {
	
	public static final String API_URL = "/api/v1";
	
	public static final String REGISTER_USER_URL = "/register-user";
	
	public static final String REGISTER_USER_FULL_URL = ProjectUtils.API_URL + "/register-user";
	
	public static final String LOGIN_URL = ProjectUtils.API_URL + "/auth/login";
	
	public static final String SUCCESS_STATUS = "SUCCESS";
	
	public static final String FAIL_STATUS = "FAIL";
	
	public static final String PENDING_STATUS = "PENDING";
	
	public static <T> T getValue(T value) {
		return value == null ? null : value;
	}

}
