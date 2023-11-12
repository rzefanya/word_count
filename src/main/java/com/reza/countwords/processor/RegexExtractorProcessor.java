package com.reza.countwords.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.model.InputText;
import com.reza.countwords.result.ExtractionResult;
import com.reza.countwords.result.ProcessingResult;

import lombok.extern.slf4j.Slf4j;

/**
 * Processor for extracting word from regex rule
 */
@Component
@Slf4j
public class RegexExtractorProcessor implements BaseProcessor {
	public String parse(CountingRule rule) {
		log.info("parse rule={}", rule);
		JSONObject json = new JSONObject(rule.getAttributes());
		return json.getString("regex");
	}

	@Override
	public ProcessingResult process(CountingRule rule, InputText text) {
		log.info("process rule={} text={}", rule, text);
		List<String> allMatches = new ArrayList<String>();
		Matcher m = Pattern.compile(parse(rule)).matcher(text.getText());
		while (m.find()) {
			allMatches.add(m.group());
		}

		ExtractionResult result = new ExtractionResult();
		result.setRule(rule);
		result.setText(text);
		result.setCount(allMatches.size());
		result.setOutput(allMatches);

		log.info("process rule={} text={} result={}", rule, text, result);
		return result;
	}
}
