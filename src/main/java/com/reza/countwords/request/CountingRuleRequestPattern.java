package com.reza.countwords.request;

import com.reza.countwords.request.base.CountingRuleBaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CountingRuleRequestPattern extends CountingRuleBaseRequest {
	String pattern;

	@Override
	public String getPattern() {
		return pattern;
	}
}
