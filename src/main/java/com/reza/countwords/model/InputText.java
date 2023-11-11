package com.reza.countwords.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

/**
 * Input text that will be processed by rule
 */
@Entity
@Data
public class InputText {
	@Id
	String filename;

	@Lob
	String text;
}
