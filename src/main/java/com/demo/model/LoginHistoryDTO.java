package com.demo.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor()
@AllArgsConstructor
public class LoginHistoryDTO {
	
	@JsonProperty("id")
    private long id;

	@JsonProperty("username")
    private String username;
    
	@JsonProperty("attemptStatus")
    private String attemptStatus;
    
	@JsonProperty("attemptAt")
    private Date attemptAt;

}
