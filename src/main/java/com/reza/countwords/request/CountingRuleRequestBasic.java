package com.reza.countwords.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reza.countwords.request.base.CountingRuleBaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Request for making new rules with starts with and minimum length.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CountingRuleRequestBasic extends CountingRuleBaseRequest {
	String startsWith;
	int minimumLength;

	/**
	 * Return regex pattern generated from startsWith and minimumLength
	 * 
	 * @return regex pattern
	 */
	@Override
	@JsonIgnore
	public String getPattern() {
		StringBuilder sb = new StringBuilder();
		sb.append("\\b[" + startsWith.toUpperCase() + startsWith.toLowerCase() + "]");
		sb.append("[A-z]");
		sb.append("{" + (minimumLength - 1) + ",}");
		return sb.toString();
	}
}
