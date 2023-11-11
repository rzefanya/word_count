package com.reza.countwords.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.model.InputText;
import com.reza.countwords.request.CountWordSavedRequest;
import com.reza.countwords.response.CountWordsResponse;

/**
 * Service for counting words from input text with rules
 */
public interface CountWordsService {
	/**
	 * Filter input text with regex pattern and return list of matches.
	 *
	 * @param pattern regex pattern to process the text input with
	 * @param input   text that will be processed by regex
	 */
	List<String> process(String pattern, String input);

	/**
	 * Filter input text with rule which are saved in database as default and return
	 * list of matches.
	 */
	CountWordsResponse countWordsDefault();

	/**
	 * Filter input text provided with default rule saved in database and return
	 * list of matches.
	 * 
	 * @param file uploaded text file
	 */
	CountWordsResponse countWordsDefaultWithText(MultipartFile file) throws IOException;

	/**
	 * Filter selected input text with selected rule saved in database and return
	 * list of matches.
	 * 
	 * @param request rulename and filename from database
	 */
	CountWordsResponse countWordsFromSavedText(CountWordSavedRequest request);

	/**
	 * Filter provided input text with rule and return list of matches.
	 * 
	 * @param rule rule to process the text input with
	 * @param text text that will be processed by rule
	 */
	CountWordsResponse countWords(CountingRule rule, InputText text);

}