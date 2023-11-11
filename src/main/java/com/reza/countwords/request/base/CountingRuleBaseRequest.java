package com.reza.countwords.request.base;

import lombok.Data;

@Data
public abstract class CountingRuleBaseRequest {
	protected String name;
	protected String description;

	public abstract String getPattern();
}
