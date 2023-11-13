package com.reza.countwords.processor;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.model.InputText;
import com.reza.countwords.result.ExtractionResult;

@ExtendWith(MockitoExtension.class)
public class StartLimitExtractorProcessorTest {
	@InjectMocks
	private StartLimitExtractorProcessor processor;

	@Test
	public void parseTest() {
		CountingRule rule = new CountingRule();
		rule.setAttributes("{\"startsWith\":\"m\",\"minimumLength\":6}");
		String result = processor.parse(rule);
		assertEquals("\\b[Mm][A-z]{5,}", result);
	}

	@Test
	public void processTest() {
		CountingRule rule = new CountingRule();
		rule.setAttributes("{\"startsWith\":\"m\",\"minimumLength\":6}");

		InputText text = new InputText();
		text.setText("microwave minds Multitasking package java");

		ExtractionResult result = (ExtractionResult) processor.process(rule, text);
		assertAll("grouped assertion of extraction result", () -> assertEquals(2, result.getCount()),
				() -> assertEquals("microwave", result.getOutput().get(0)),
				() -> assertEquals("Multitasking", result.getOutput().get(1)));
	}
}
