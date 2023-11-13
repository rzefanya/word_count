package com.reza.countwords.util;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.reza.countwords.response.ResponseWrapper;

/**
 * Utility class for generating response
 */
public class ResponseUtil {

	/**
	 * generate response with ok status
	 */
	public static <T> ResponseEntity<ResponseWrapper<T>> ok(T data) {
		ResponseWrapper<T> response = new ResponseWrapper<>();
		response.setTimestamp(LocalDateTime.now());
		response.setData(data);
		response.setMessage("success");
		response.setStatus(HttpStatus.OK);
		return ResponseEntity.ok(response);
	}

	/**
	 * generate response with any status
	 */
	public static <T> ResponseEntity<Object> buildError(String message, HttpStatus status, List<String> errors) {
		ResponseWrapper<T> response = new ResponseWrapper<>();
		response.setTimestamp(LocalDateTime.now());
		response.setMessage(message);
		response.setStatus(status);
		response.setErrors(errors);
		return new ResponseEntity<>(response, status);
	}
}
