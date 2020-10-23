package com.estabild.demo.estabilddemo;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import com.demo.EstabildDemoApplication;
import com.demo.entity.User;
import com.demo.filter.JWTAuthenticationFilter;
import com.demo.model.UserDTO;
import com.demo.repository.UserRepository;
import com.demo.utils.ProjectUtils;
import com.demo.utils.UserConverter;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EstabildDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EstabildDemoApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConverter userConverter;

	@LocalServerPort
	private int port;

	@Test
	public void contextLoads() {
	}

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	private UserDTO getUserDTO() {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("admin@gmail.com");
		userDTO.setFirstName("admin");
		userDTO.setLastName("admin");
		userDTO.setCreatedBy("admin");
		userDTO.setUpdatedBy("admin");
		userDTO.setCreatedAt(new Date());
		userDTO.setUpdatedAt(new Date());
		userDTO.setUsername("admin");
		userDTO.setPassword("admin");
		return userDTO;
	}

	@Test
	public void testRegisterUser() {
		UserDTO userDTO = getUserDTO();
		ResponseEntity<UserDTO> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/register-user",
				userDTO, UserDTO.class);
		Assert.notNull(postResponse, "response should not be null");
		Assert.notNull(postResponse.getBody(), "response body should not be null");
		User responseUser = userConverter.convertFromDto(postResponse.getBody());
		Assert.isTrue(userDTO.getUsername().equals(postResponse.getBody().getUsername()),
				"response username should match");
		userRepository.delete(responseUser);
	}

	@Test
	public void testRegisterUserAndLogin() throws JSONException {
		UserDTO userDTO = getUserDTO();
		ResponseEntity<UserDTO> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/register-user",
				userDTO, UserDTO.class);
		Assert.isTrue(postResponse.getStatusCode().equals(HttpStatus.CREATED), "response status");
		User responseUser = userConverter.convertFromDto(postResponse.getBody());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject userlogin = new JSONObject();
		userlogin.put("username", "admin");
		userlogin.put("password", "admin");
		HttpEntity<String> request = new HttpEntity<String>(userlogin.toString(), headers);
		HttpEntity<String> response = restTemplate.exchange(getRootUrl() + ProjectUtils.LOGIN_URL, HttpMethod.POST,
				request, String.class);
		HttpHeaders responseHeaders = response.getHeaders();
		Assert.isTrue(responseHeaders.get(JWTAuthenticationFilter.HEADER_STRING).get(0)
				.startsWith(JWTAuthenticationFilter.TOKEN_PREFIX), "response authorization token should start with");
		userRepository.delete(responseUser);
	}

	@Test
	public void testLoginHistory() throws JSONException {
		UserDTO userDTO = getUserDTO();
		ResponseEntity<UserDTO> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/register-user",
				userDTO, UserDTO.class);
		Assert.isTrue(postResponse.getStatusCode().equals(HttpStatus.CREATED), "response status");
		User responseUser = userConverter.convertFromDto(postResponse.getBody());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject userlogin = new JSONObject();
		userlogin.put("username", "admin");
		userlogin.put("password", "admin");
		HttpEntity<String> request = new HttpEntity<String>(userlogin.toString(), headers);
		HttpEntity<String> response = restTemplate.exchange(getRootUrl() + ProjectUtils.LOGIN_URL, HttpMethod.POST,
				request, String.class);
		HttpHeaders responseHeaders = response.getHeaders();
		HttpHeaders headersForLoginHistory = new HttpHeaders();
		headersForLoginHistory.setContentType(MediaType.APPLICATION_JSON);
		headersForLoginHistory.add(JWTAuthenticationFilter.HEADER_STRING,
				responseHeaders.get(JWTAuthenticationFilter.HEADER_STRING).get(0));
		HttpEntity<String> requestForloginHistory = new HttpEntity<String>(null, headersForLoginHistory);
		HttpEntity<String> responseFromLogingHistory = restTemplate.exchange(
				getRootUrl() + "/api/v1/login-history/admin", HttpMethod.GET, requestForloginHistory, String.class);
		String responseBody = responseFromLogingHistory.getBody();
		Assert.notNull(responseBody, "Login history should not be null");
		userRepository.delete(responseUser);
	}

}
