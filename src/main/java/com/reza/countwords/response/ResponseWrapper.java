package com.reza.countwords.response;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ResponseWrapper<T> extends Object{
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	LocalDateTime timestamp;
	HttpStatus status;
	String message;
	List<String> errors;
	T data;
}
