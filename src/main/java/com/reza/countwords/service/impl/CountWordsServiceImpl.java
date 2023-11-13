package com.reza.countwords.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.model.InputText;
import com.reza.countwords.processor.BaseProcessor;
import com.reza.countwords.request.CountWordSavedRequest;
import com.reza.countwords.result.ProcessingResult;
import com.reza.countwords.service.CountWordsService;
import com.reza.countwords.service.CountingRuleService;
import com.reza.countwords.service.InputTextService;
import com.reza.countwords.service.ProcessorService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CountWordsServiceImpl implements CountWordsService {
	@Autowired
	private CountingRuleService countingRuleService;

	@Autowired
	private InputTextService inputTextService;

	@Autowired
	private ProcessorService processorService;

	@Override
	public ProcessingResult countWordsDefault() {
		log.info("countWordsDefault");
		CountWordSavedRequest request = new CountWordSavedRequest();
		request.setFilename("default");
		request.setRuleName("default");
		return countWordsFromSavedText(request);
	}

	@Override
	public ProcessingResult countWordsDefaultWithText(MultipartFile file) throws IOException {
		log.info("countWordsDefaultWithText file={}", file.getOriginalFilename());
		CountingRule rule = countingRuleService.findByName("default");
		InputText text = inputTextService.processText(file);
		return countWords(rule, text);
	}

	@Override
	public ProcessingResult countWordsFromSavedText(CountWordSavedRequest request) {
		log.info("countWordsFromSavedText {}", request);
		CountingRule rule = countingRuleService.findByName(request.getRuleName());
		InputText text = inputTextService.findByFilename(request.getFilename());
		return countWords(rule, text);
	}

	@Override
	public ProcessingResult countWords(CountingRule rule, InputText text) {
		log.info("countWords rule={} text={}", rule, text);

		BaseProcessor processor = processorService.getProcessor(rule);
		return processor.process(rule, text);
	}
}
