package com.reza.countwords.request;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reza.countwords.model.CountingRule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Request for making new rules with starts with and minimum length.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CountingRuleRequestBasic extends CountingRule {
	String startsWith;
	int minimumLength;

	@Override
	@JsonIgnore
	public String getProcessor() {
		return "startLimitExtractorProcessor";
	}

	@Override
	public String getAttributes() {
		JSONObject json = new JSONObject();
		json.put("startsWith", startsWith);
		json.put("minimumLength", minimumLength);
		return json.toString();
	}

}
