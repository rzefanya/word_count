package com.reza.countwords.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.model.InputText;
import com.reza.countwords.processor.RegexExtractorProcessor;
import com.reza.countwords.request.CountWordSavedRequest;
import com.reza.countwords.result.ExtractionResult;
import com.reza.countwords.service.impl.CountWordsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CountWordsServiceImplTest {
	@Mock
	private CountingRuleService countingRuleService;

	@Mock
	private InputTextService inputTextService;

	@Mock
	private ProcessorService processorService;

	@InjectMocks
	private CountWordsServiceImpl service;

	@Test
	public void countWordsDefaultTest() {
		CountingRule rule = new CountingRule();
		rule.setName("default");
		rule.setAttributes("{\"regex\":\"\\\\b[Mm][A-z]{5,}\"}");
		rule.setProcessor("regexExtractorProcessor");
		when(countingRuleService.findByName("default")).thenReturn(rule);

		InputText text = new InputText();
		text.setFilename("default");
		text.setText("microwave minds Multitasking package java");
		when(inputTextService.findByFilename("default")).thenReturn(text);

		when(processorService.getProcessor(rule)).thenReturn(new RegexExtractorProcessor());

		ExtractionResult result = (ExtractionResult) service.countWordsDefault();
		assertAll("grouped assertion of result", () -> assertEquals(rule, result.getRule()),
				() -> assertEquals(text, result.getText()), () -> assertEquals(2, result.getCount()),
				() -> assertEquals("microwave", result.getOutput().get(0)),
				() -> assertEquals("Multitasking", result.getOutput().get(1)));
	}

	@Test
	public void countWordsDefaultWithTextTest() throws IOException {
		CountingRule rule = new CountingRule();
		rule.setName("default");
		rule.setAttributes("{\"regex\":\"\\\\b[Mm][A-z]{5,}\"}");
		rule.setProcessor("regexExtractorProcessor");
		when(countingRuleService.findByName("default")).thenReturn(rule);

		Resource fileResource = new ClassPathResource("text1.txt");
		assertNotNull(fileResource);

		MockMultipartFile file = new MockMultipartFile("file", fileResource.getFilename(),
				MediaType.MULTIPART_FORM_DATA_VALUE, fileResource.getInputStream());
		assertNotNull(file);

		InputText text = new InputText();
		text.setFilename("default");
		text.setText("microwave minds Multitasking package java");
		when(inputTextService.processText(file)).thenReturn(text);

		when(processorService.getProcessor(rule)).thenReturn(new RegexExtractorProcessor());

		ExtractionResult result = (ExtractionResult) service.countWordsDefaultWithText(file);
		assertAll("grouped assertion of result", () -> assertEquals(rule, result.getRule()),
				() -> assertEquals(text, result.getText()), () -> assertEquals(2, result.getCount()),
				() -> assertEquals("microwave", result.getOutput().get(0)),
				() -> assertEquals("Multitasking", result.getOutput().get(1)));
	}

	@Test
	public void countWordsFromSavedTextTest() {
		CountingRule rule = new CountingRule();
		rule.setName("rule1");
		rule.setAttributes("{\"regex\":\"\\\\b[Mm][A-z]{5,}\"}");
		rule.setProcessor("regexExtractorProcessor");
		when(countingRuleService.findByName("rule1")).thenReturn(rule);

		InputText text = new InputText();
		text.setFilename("text1");
		text.setText("microwave minds Multitasking package java");
		when(inputTextService.findByFilename("text1")).thenReturn(text);

		when(processorService.getProcessor(rule)).thenReturn(new RegexExtractorProcessor());

		CountWordSavedRequest request = new CountWordSavedRequest();
		request.setFilename("text1");
		request.setRuleName("rule1");
		ExtractionResult result = (ExtractionResult) service.countWordsFromSavedText(request);
		assertAll("grouped assertion of result", () -> assertEquals(rule, result.getRule()),
				() -> assertEquals(text, result.getText()), () -> assertEquals(2, result.getCount()),
				() -> assertEquals("microwave", result.getOutput().get(0)),
				() -> assertEquals("Multitasking", result.getOutput().get(1)));
	}

	@Test
	public void countWordsTest() {
		CountingRule rule = new CountingRule();
		rule.setName("rule1");
		rule.setAttributes("{\"regex\":\"\\\\b[Mm][A-z]{5,}\"}");
		rule.setProcessor("regexExtractorProcessor");

		InputText text = new InputText();
		text.setFilename("text1");
		text.setText("microwave minds Multitasking package java");

		when(processorService.getProcessor(rule)).thenReturn(new RegexExtractorProcessor());

		ExtractionResult result = (ExtractionResult) service.countWords(rule, text);
		assertAll("grouped assertion of result", () -> assertEquals(rule, result.getRule()),
				() -> assertEquals(text, result.getText()), () -> assertEquals(2, result.getCount()),
				() -> assertEquals("microwave", result.getOutput().get(0)),
				() -> assertEquals("Multitasking", result.getOutput().get(1)));
	}
}
