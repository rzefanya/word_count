package com.reza.countwords.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.reza.countwords.exception.ResourceNotFoundException;
import com.reza.countwords.model.CountingRule;
import com.reza.countwords.model.InputText;
import com.reza.countwords.result.ExtractionResult;
import com.reza.countwords.service.CountWordsService;

@WebMvcTest(CountWordsController.class)
public class CountWordsControllerTest {

	@MockBean
	private CountWordsService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void countWordDefaultTest() throws Exception {
		ExtractionResult result = new ExtractionResult();
		result.setRule(new CountingRule());
		result.setText(new InputText());
		result.setCount(1);
		result.setOutput(Arrays.asList("one"));
		when(service.countWordsDefault()).thenReturn(result);

		mockMvc.perform(post("/countwords/default")).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.count", is(1))).andExpect(jsonPath("$.data.output[0]", is("one")));
	}

	@Test
	public void countWordDefaultWithTextTest() throws Exception {
		ExtractionResult result = new ExtractionResult();
		result.setRule(new CountingRule());
		result.setText(new InputText());
		result.setCount(1);
		result.setOutput(Arrays.asList("one"));
		when(service.countWordsDefaultWithText(any())).thenReturn(result);

		Resource fileResource = new ClassPathResource("text1.txt");
		assertNotNull(fileResource);

		MockMultipartFile file = new MockMultipartFile("file", fileResource.getFilename(),
				MediaType.MULTIPART_FORM_DATA_VALUE, fileResource.getInputStream());
		assertNotNull(file);

		mockMvc.perform(multipart("/countwords/defaultWithText").file(file)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.count", is(1))).andExpect(jsonPath("$.data.output[0]", is("one")));
	}

	@Test
	public void countWordSavedTest() throws Exception {
		ExtractionResult result = new ExtractionResult();
		result.setRule(new CountingRule());
		result.setText(new InputText());
		result.setCount(1);
		result.setOutput(Arrays.asList("one"));
		when(service.countWordsFromSavedText(any())).thenReturn(result);

		mockMvc.perform(post("/countwords/saved").contentType(MediaType.APPLICATION_JSON)
				.content("{\"ruleName\":\"rule\",\"filename\":\"file\"}")).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.count", is(1))).andExpect(jsonPath("$.data.output[0]", is("one")));
	}

	@Test
	public void countWordSavedTestNotFound() throws Exception {
		when(service.countWordsFromSavedText(any())).thenThrow(new ResourceNotFoundException("rule name not found"));

		mockMvc.perform(post("/countwords/saved").contentType(MediaType.APPLICATION_JSON)
				.content("{\"ruleName\":\"rule\",\"filename\":\"file\"}")).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errors[0]", is("rule name not found")));
	}
}
