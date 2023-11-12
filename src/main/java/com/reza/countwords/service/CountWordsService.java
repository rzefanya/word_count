package com.reza.countwords.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.model.InputText;
import com.reza.countwords.request.CountWordSavedRequest;
import com.reza.countwords.result.ProcessingResult;

/**
 * Service for counting words from input text with rules
 */
public interface CountWordsService {
	/**
	 * Filter input text with rule which are saved in database as default and return
	 * list of matches.
	 */
	ProcessingResult countWordsDefault();

	/**
	 * Filter input text provided with default rule saved in database and return
	 * list of matches.
	 * 
	 * @param file uploaded text file
	 */
	ProcessingResult countWordsDefaultWithText(MultipartFile file) throws IOException;

	/**
	 * Filter selected input text with selected rule saved in database and return
	 * list of matches.
	 * 
	 * @param request rulename and filename from database
	 */
	ProcessingResult countWordsFromSavedText(CountWordSavedRequest request);

	/**
	 * Filter provided input text with rule and return list of matches.
	 * 
	 * @param rule rule to process the text input with
	 * @param text text that will be processed by rule
	 */
	ProcessingResult countWords(CountingRule rule, InputText text);

}