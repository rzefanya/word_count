package com.reza.countwords.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Rule for processing input text
 */
@Entity
@Data
public class CountingRule {
	@Id
	@NotBlank(message = "name is mandatory")
	String name;
	String description;
	String processor;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	String attributes;
}
