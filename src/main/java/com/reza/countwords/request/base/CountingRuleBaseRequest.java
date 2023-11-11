package com.reza.countwords.request.base;

import lombok.Data;

/**
 * Base request for making new rules.
 * 
 * Extend this class to create new rules from additional params.
 * 
 * That way no need to change the main logic when we implement new type of rules
 */
@Data
public abstract class CountingRuleBaseRequest {
	protected String name;
	protected String description;

	/**
	 * Return regex pattern generated from additional params
	 * 
	 * @return regex pattern
	 */
	public abstract String getPattern();
}
