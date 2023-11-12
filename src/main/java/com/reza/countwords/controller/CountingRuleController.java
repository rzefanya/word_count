package com.reza.countwords.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.request.CountingRuleRequestBasic;
import com.reza.countwords.request.CountingRuleRequestRegex;
import com.reza.countwords.service.CountingRuleService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rule")
@Slf4j
public class CountingRuleController {

	@Autowired
	private CountingRuleService service;

	@Operation(summary = "Save rule created with regex")
	@PostMapping("/saveRegex")
	public CountingRule save(@RequestBody CountingRuleRequestRegex request) {
		log.info("save request={}", request);
		return service.addRule(request);
	}

	@Operation(summary = "Save rule that start with and length")
	@PostMapping("/saveBasic")
	public CountingRule save(@RequestBody CountingRuleRequestBasic request) {
		log.info("saveBasic request={}", request);
		return service.addRule(request);
	}

	@Operation(summary = "Get rules")
	@GetMapping
	public Page<CountingRule> findAll(@ParameterObject Pageable pageable) {
		log.info("findAll request={}", pageable);
		return service.findAll(pageable);
	}

	@Operation(summary = "Get rule by name")
	@GetMapping("/{name}")
	public CountingRule findByName(@PathVariable String name) {
		log.info("findByName request={}", name);
		return service.findByName(name);
	}

	@Operation(summary = "Delete rule by name")
	@DeleteMapping("/{name}")
	public CountingRule deleteByName(@PathVariable String name) {
		log.info("deleteByName request={}", name);
		return service.deleteByName(name);
	}
}
