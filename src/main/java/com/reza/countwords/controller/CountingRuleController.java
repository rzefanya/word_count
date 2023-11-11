package com.reza.countwords.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.request.CountingRuleRequestBasic;
import com.reza.countwords.request.CountingRuleRequestPattern;
import com.reza.countwords.service.impl.CountingRuleService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rule")
@Slf4j
public class CountingRuleController {

	@Autowired
	private CountingRuleService service;

	@PostMapping
	public CountingRule save(@RequestBody CountingRuleRequestPattern request) {
		log.info("save {}", request);
		return service.addRule(request);
	}

	@PostMapping("/saveBasic")
	public CountingRule save(@RequestBody CountingRuleRequestBasic request) {
		log.info("saveBasic {}", request);
		return service.addRule(request);
	}

	@GetMapping
	public Page<CountingRule> findAll(Pageable pageable) {
		return service.findAll(pageable);
	}

	@GetMapping("/{name}")
	public CountingRule findByName(@RequestParam String name) {
		return service.findByName(name);
	}
}
