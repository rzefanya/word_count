package com.reza.countwords.result;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/*
 * Processing result of word count and extraction
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ExtractionResult extends ProcessingResult {
	long count;
	List<String> output;
}
