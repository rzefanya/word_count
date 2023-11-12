package com.reza.countwords.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.processor.BaseProcessor;
import com.reza.countwords.processor.RegexExtractorProcessor;
import com.reza.countwords.processor.StartLimitExtractorProcessor;
import com.reza.countwords.service.ProcessorService;

import jakarta.annotation.PostConstruct;

@Service
public class ProcessorServiceImpl implements ProcessorService {
	@Autowired
	private RegexExtractorProcessor regexExtractorProcessor;

	@Autowired
	private StartLimitExtractorProcessor startLimitExtractorProcessor;

	private Map<String, BaseProcessor> processorMap;

	@PostConstruct
	public void initProcessorMap() {
		processorMap = new HashMap<>();
		processorMap.put("regexExtractorProcessor", regexExtractorProcessor);
		processorMap.put("startLimitExtractorProcessor", startLimitExtractorProcessor);
	}

	@Override
	public BaseProcessor getProcessor(CountingRule rule) {
		return processorMap.get(rule.getProcessor());
	}
}
