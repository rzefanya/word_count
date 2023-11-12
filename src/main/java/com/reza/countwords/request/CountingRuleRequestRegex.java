package com.reza.countwords.request;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reza.countwords.model.CountingRule;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Request for making new rules with regex pattern.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CountingRuleRequestRegex extends CountingRule {
	@NotBlank
	String regex;

	@Override
	@JsonIgnore
	public String getProcessor() {
		return "regexExtractorProcessor";
	}

	@Override
	public String getAttributes() {
		JSONObject json = new JSONObject();
		json.put("regex", regex);
		return json.toString();
	}
}
