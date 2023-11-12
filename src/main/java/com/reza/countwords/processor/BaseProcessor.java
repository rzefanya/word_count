package com.reza.countwords.processor;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.model.InputText;
import com.reza.countwords.result.ProcessingResult;

/**
 * Base Processor to be implemented for processing rule
 */
public interface BaseProcessor {

	/**
	 * Process text using provided rule
	 */
	ProcessingResult process(CountingRule rule, InputText text);

}