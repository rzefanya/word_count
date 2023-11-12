package com.reza.countwords.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.reza.countwords.request.CountWordSavedRequest;
import com.reza.countwords.result.ProcessingResult;
import com.reza.countwords.service.CountWordsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/countwords")
@Slf4j
public class CountWordsController {

	@Autowired
	private CountWordsService service;

	@PostMapping(value = "/default")
	public ProcessingResult countWordDefault() {
		log.info("countWordDefault");
		return service.countWordsDefault();
	}

	@PostMapping(value = "/defaultWithText", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ProcessingResult countWordDefaultWithText(@RequestParam MultipartFile file) throws IOException {
		log.info("countWordDefaultWithText file={}", file.getOriginalFilename());
		return service.countWordsDefaultWithText(file);
	}

	@PostMapping(value = "/saved")
	public ProcessingResult countWordSaved(@RequestBody CountWordSavedRequest request) {
		log.info("countWordSaved request={}", request);
		return service.countWordsFromSavedText(request);
	}
}
