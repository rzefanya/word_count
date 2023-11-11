package com.reza.countwords.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Rule for processing input text
 */
@Entity
@Data
public class CountingRule {
	@Id
	String name;
	String description;
	String pattern;
}
