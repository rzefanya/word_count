package com.reza.countwords.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.reza.countwords.model.CountingRule;

public interface CountingRuleService {

	CountingRule addRule(CountingRule request);

	CountingRule findByName(String name);

	Page<CountingRule> findAll(Pageable pageable);

	CountingRule deleteByName(String name);

}