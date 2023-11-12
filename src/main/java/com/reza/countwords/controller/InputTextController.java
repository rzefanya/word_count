package com.reza.countwords.controller;

import java.io.IOException;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.reza.countwords.model.InputText;
import com.reza.countwords.service.InputTextService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/text")
@Slf4j
public class InputTextController {

	@Autowired
	private InputTextService service;

	@Operation(summary = "Save text using uploaded file")
	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public InputText uploadFile(@RequestParam MultipartFile file) throws IOException {
		log.info("uploadFile {}", file.getName());
		return service.addText(file);
	}

	@Operation(summary = "Get saved texts")
	@GetMapping
	public Page<InputText> findAll(@ParameterObject Pageable pageable) {
		log.info("findAll {}", pageable);
		return service.findAll(pageable);
	}

	@Operation(summary = "Get saved text by filename")
	@GetMapping("/{filename}")
	public InputText findByName(@RequestParam String filename) {
		log.info("findByName {}", filename);
		return service.findByFilename(filename);
	}

	@Operation(summary = "Delete saved text by filename")
	@DeleteMapping("/{name}")
	public InputText deleteByFilename(@PathVariable String name) {
		log.info("deleteByName request={}", name);
		return service.deleteByFilename(name);
	}
}
