package com.reza.countwords.controller;

import java.io.IOException;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.reza.countwords.model.InputText;
import com.reza.countwords.response.ResponseWrapper;
import com.reza.countwords.service.InputTextService;
import com.reza.countwords.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/text")
@Slf4j
public class InputTextController {

	@Autowired
	private InputTextService service;

	@Operation(summary = "Save text using uploaded file")
	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ResponseWrapper<InputText>> uploadFile(@RequestParam MultipartFile file) throws IOException {
		log.info("uploadFile {}", file.getName());
		return ResponseUtil.ok(service.addText(file));
	}

	@Operation(summary = "Get saved texts")
	@GetMapping
	public ResponseEntity<ResponseWrapper<Page<InputText>>> findAll(@ParameterObject Pageable pageable) {
		log.info("findAll {}", pageable);
		return ResponseUtil.ok(service.findAll(pageable));
	}

	@Operation(summary = "Get saved text by filename")
	@ApiResponses(value = { @ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(implementation = ResponseWrapper.class)) }) })
	@GetMapping("/{filename}")
	public ResponseEntity<ResponseWrapper<InputText>> findByName(@PathVariable String filename) {
		log.info("findByName {}", filename);
		return ResponseUtil.ok(service.findByFilename(filename));
	}

	@Operation(summary = "Delete saved text by filename")
	@ApiResponses(value = { @ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(implementation = ResponseWrapper.class)) }) })
	@DeleteMapping("/{filename}")
	public ResponseEntity<ResponseWrapper<InputText>> deleteByFilename(@PathVariable String filename) {
		log.info("deleteByName request={}", filename);
		return ResponseUtil.ok(service.deleteByFilename(filename));
	}
}
