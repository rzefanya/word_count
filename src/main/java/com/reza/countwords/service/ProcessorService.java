package com.reza.countwords.service;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.processor.BaseProcessor;

/**
 * Service to get processor from rule
 */
public interface ProcessorService {

	/**
	 * get correct processor based on provided rule
	 */
	BaseProcessor getProcessor(CountingRule rule);

}