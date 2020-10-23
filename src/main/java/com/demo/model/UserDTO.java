package com.demo.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor()
@AllArgsConstructor
public class UserDTO {
	
	@JsonProperty("id")
    private long id;
	
	@NotNull(message = "Please provide username")
	@JsonProperty("username")
    private String username;
	
	@JsonProperty("password")
    private String password;
	
	@JsonProperty("firstName")
    private String firstName;
	
	@JsonProperty("lastName")
    private String lastName;
	
	@JsonProperty("email")
    private String email;
	
	@JsonProperty("createdAt")
    private Date createdAt;
	
	@JsonProperty("createdBy")
    private String createdBy;
	
	@JsonProperty("updatedAt")
    private Date updatedAt;
	
	@JsonProperty("updatedBy")
    private String updatedBy;
	
	@JsonIgnore
	@JsonProperty(value = "password")
	public String getPassword() {
		return password;
	}

}
