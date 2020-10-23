
package com.demo.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error response.
 *
 * 
 */
@Data
@NoArgsConstructor()
@AllArgsConstructor
public class ErrorResponse {

  private Date timestamp;
  private String status;
  private String message;
  private String details;



}
