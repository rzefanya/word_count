package com.reza.countwords.request;

import lombok.Data;

/**
 * Request for counting words from rule and text saved in database
 */
@Data
public class CountWordSavedRequest {
	String ruleName;
	String filename;
}
