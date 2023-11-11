package com.reza.countwords.response;

import java.util.List;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.model.InputText;

import lombok.Data;

/**
 * Response object for storing word counting result
 */
@Data
public class CountWordsResponse {
	CountingRule rule;
	InputText text;
	long count;
	List<String> output;
}
