package com.reza.countwords.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request for counting words from rule and text saved in database
 */
@Data
public class CountWordSavedRequest {
	@NotBlank(message = "ruleName is mandatory")
	String ruleName;

	@NotBlank(message = "filename is mandatory")
	String filename;
}
