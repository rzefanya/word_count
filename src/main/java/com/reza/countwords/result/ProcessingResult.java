package com.reza.countwords.result;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.model.InputText;

import lombok.Data;

/*
 * Base processing result, extend to support more types of processing
 */
@Data
public abstract class ProcessingResult {
	CountingRule rule;
	InputText text;
}
