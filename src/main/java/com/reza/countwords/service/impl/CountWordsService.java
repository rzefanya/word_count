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

@Service
public class CountWordsService {
	@Autowired
	private CountingRuleService countingRuleService;

	@Autowired
	private InputTextService inputTextService;

	public List<String> process(String pattern, String input) {
		List<String> allMatches = new ArrayList<String>();
		Matcher m = Pattern.compile(pattern).matcher(input);
		while (m.find()) {
			allMatches.add(m.group());
		}

		return allMatches;
	}

	public CountWordsResponse countWordsDefault() {
		CountWordSavedRequest request = new CountWordSavedRequest();
		request.setFilename("default");
		request.setRuleName("default");
		return countWordsFromSavedText(request);
	}

	public CountWordsResponse countWordsDefaultWithText(MultipartFile file) throws IOException {
		CountingRule rule = countingRuleService.findByName("default");
		InputText text = inputTextService.processText(file);
		return countWords(rule, text);
	}

	public CountWordsResponse countWordsFromSavedText(CountWordSavedRequest request) {
		CountingRule rule = countingRuleService.findByName(request.getRuleName());
		InputText text = inputTextService.findByFilename(request.getFilename());
		return countWords(rule, text);
	}

	public CountWordsResponse countWords(CountingRule rule, InputText text) {
		List<String> output = process(rule.getPattern(), text.getText());
		CountWordsResponse response = new CountWordsResponse();
		response.setRule(rule);
		response.setText(text);
		response.setCount(output.size());
		response.setOutput(output);
		return response;
	}
}
