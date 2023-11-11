package com.reza.countwords.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.reza.countwords.model.InputText;
import com.reza.countwords.service.impl.InputTextService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/text")
@Slf4j
public class InputTextController {

	@Autowired
	private InputTextService service;

	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public InputText uploadFile(@RequestParam MultipartFile file) throws IOException {
		log.info("uploadFile {}", file.getName());
		return service.addText(file);
	}

	@GetMapping
	public Page<InputText> findAll(Pageable pageable) {
		return service.findAll(pageable);
	}

	@GetMapping("/{filename}")
	public InputText findByName(@RequestParam String filename) {
		return service.findByFilename(filename);
	}
}
