package com.reza.countwords.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.repository.CountingRuleRepository;
import com.reza.countwords.request.base.CountingRuleBaseRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CountingRuleService {
	@Autowired
	private CountingRuleRepository repo;

	public CountingRule addRule(CountingRuleBaseRequest request) {
		log.info("add Rule start request={}", request);
		CountingRule rule = new CountingRule();
		rule.setName(request.getName());
		rule.setDescription(request.getDescription());
		rule.setPattern(request.getPattern());

		log.info("add Rule rule={}", rule);

		rule = repo.save(rule);

		log.info("add Rule end rule={}", rule);

		return rule;
	}

	public CountingRule findByName(String name) {
		Optional<CountingRule> rule = repo.findById(name);

		if (rule.isPresent()) {
			return rule.get();
		} else {
			return null;
		}
	}

	public Page<CountingRule> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}
}
