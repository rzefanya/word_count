package com.reza.countwords.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.model.InputText;
import com.reza.countwords.request.CountWordSavedRequest;
import com.reza.countwords.response.CountWordsResponse;
import com.reza.countwords.service.CountWordsService;
import com.reza.countwords.service.CountingRuleService;
import com.reza.countwords.service.InputTextService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CountWordsServiceImpl implements CountWordsService {
	@Autowired
	private CountingRuleService countingRuleService;

	@Autowired
	private InputTextService inputTextService;

	@Override
	public List<String> process(String pattern, String input) {
		log.info("process pattern={} input={}", pattern, input);
		List<String> allMatches = new ArrayList<String>();
		Matcher m = Pattern.compile(pattern).matcher(input);
		while (m.find()) {
			allMatches.add(m.group());
		}

		log.info("process pattern={} input={} allMatches={}", pattern, input, allMatches);

		return allMatches;
	}

	@Override
	public CountWordsResponse countWordsDefault() {
		log.info("countWordsDefault");
		CountWordSavedRequest request = new CountWordSavedRequest();
		request.setFilename("default");
		request.setRuleName("default");
		return countWordsFromSavedText(request);
	}

	@Override
	public CountWordsResponse countWordsDefaultWithText(MultipartFile file) throws IOException {
		log.info("countWordsDefaultWithText file={}", file.getOriginalFilename());
		CountingRule rule = countingRuleService.findByName("default");
		InputText text = inputTextService.processText(file);
		return countWords(rule, text);
	}

	@Override
	public CountWordsResponse countWordsFromSavedText(CountWordSavedRequest request) {
		log.info("countWordsFromSavedText {}", request);
		CountingRule rule = countingRuleService.findByName(request.getRuleName());
		InputText text = inputTextService.findByFilename(request.getFilename());
		return countWords(rule, text);
	}

	@Override
	public CountWordsResponse countWords(CountingRule rule, InputText text) {
		log.info("countWords rule={} text={}", rule, text);
		List<String> output = process(rule.getPattern(), text.getText());
		CountWordsResponse response = new CountWordsResponse();
		response.setRule(rule);
		response.setText(text);
		response.setCount(output.size());
		response.setOutput(output);
		log.info("countWords response={}", response);
		return response;
	}
}
