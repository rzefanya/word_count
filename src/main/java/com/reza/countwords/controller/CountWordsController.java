package com.reza.countwords.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.reza.countwords.request.CountWordSavedRequest;
import com.reza.countwords.response.ResponseWrapper;
import com.reza.countwords.result.ProcessingResult;
import com.reza.countwords.service.CountWordsService;
import com.reza.countwords.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/countwords")
@Slf4j
public class CountWordsController {

	@Autowired
	private CountWordsService service;

	@Operation(summary = "Process default text using default rule")
	@PostMapping(value = "/default")
	public ResponseEntity<ResponseWrapper<ProcessingResult>> countWordDefault() {
		log.info("countWordDefault");
		return ResponseUtil.ok(service.countWordsDefault());
	}

	@Operation(summary = "Process uploaded text using default rule")
	@PostMapping(value = "/defaultWithText", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ResponseWrapper<ProcessingResult>> countWordDefaultWithText(@RequestParam MultipartFile file)
			throws IOException {
		log.info("countWordDefaultWithText file={}", file.getOriginalFilename());
		return ResponseUtil.ok(service.countWordsDefaultWithText(file));
	}

	@Operation(summary = "Process saved text using saved rule")
	@ApiResponses(value = { @ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(implementation = ResponseWrapper.class)) }) })
	@PostMapping(value = "/saved")
	public ResponseEntity<ResponseWrapper<ProcessingResult>> countWordSaved(
			@RequestBody CountWordSavedRequest request) {
		log.info("countWordSaved request={}", request);
		return ResponseUtil.ok(service.countWordsFromSavedText(request));
	}
}
