package com.reza.countwords.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.reza.countwords.exception.ResourceNotFoundException;
import com.reza.countwords.model.CountingRule;
import com.reza.countwords.repository.CountingRuleRepository;
import com.reza.countwords.service.CountingRuleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CountingRuleServiceImpl implements CountingRuleService {
	@Autowired
	private CountingRuleRepository repo;

	@Override
	public CountingRule addRule(CountingRule request) {
		log.info("add Rule start request={}", request);

		CountingRule rule = new CountingRule();
		rule.setAttributes(request.getAttributes());
		rule.setDescription(request.getDescription());
		rule.setName(request.getName());
		rule.setProcessor(request.getProcessor());
		rule = repo.save(rule);

		log.info("add Rule end rule={}", rule);

		return request;
	}

	@Override
	public CountingRule findByName(String name) {
		log.info("findByName name={}", name);
		Optional<CountingRule> rule = repo.findById(name);

		if (rule.isPresent()) {
			log.info("findByName name={} present", name);
			return rule.get();
		} else {
			log.info("findByName name={} null", name);
			throw new ResourceNotFoundException(String.format("rule %s not found", name));
		}
	}

	@Override
	public Page<CountingRule> findAll(Pageable pageable) {
		log.info("findAll pageable={}", pageable);
		return repo.findAll(pageable);
	}

	@Override
	public CountingRule deleteByName(String name) {
		log.info("deleteByName name={}", name);
		CountingRule rule = findByName(name);
		if (rule != null) {
			repo.delete(rule);
		} else {
			throw new ResourceNotFoundException(String.format("rule %s not found", name));
		}
		return rule;
	}
}
