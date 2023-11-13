package com.reza.countwords.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.processor.BaseProcessor;
import com.reza.countwords.processor.RegexExtractorProcessor;
import com.reza.countwords.processor.StartLimitExtractorProcessor;
import com.reza.countwords.service.impl.ProcessorServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProcessorServiceImplTest {
	@Mock
	private RegexExtractorProcessor regexExtractorProcessor;

	@Mock
	private StartLimitExtractorProcessor startLimitExtractorProcessor;

	@InjectMocks
	private ProcessorServiceImpl service;

	@Test
	public void getProcessorTest() {
		service.initProcessorMap();

		CountingRule rule = new CountingRule();
		rule.setProcessor("regexExtractorProcessor");

		BaseProcessor result = service.getProcessor(rule);
		assertEquals(regexExtractorProcessor, result);
		
		rule.setProcessor("startLimitExtractorProcessor");

		BaseProcessor result2 = service.getProcessor(rule);
		assertEquals(startLimitExtractorProcessor, result2);
	}
}
