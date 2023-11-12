package com.reza.countwords.processor;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.reza.countwords.model.CountingRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Processor for extracting word from basic rule
 */
@Component
@Slf4j
public class StartLimitExtractorProcessor extends RegexExtractorProcessor {
	@Override
	public String parse(CountingRule rule) {
		log.info("parse rule={}", rule);

		JSONObject json = new JSONObject(rule.getAttributes());
		String startsWith = json.getString("startsWith");
		int minimumLength = json.getInt("minimumLength");

		StringBuilder sb = new StringBuilder();
		sb.append("\\b[" + startsWith.toUpperCase() + startsWith.toLowerCase() + "]");
		sb.append("[A-z]");
		sb.append("{" + (minimumLength - 1) + ",}");
		return sb.toString();
	}
}
