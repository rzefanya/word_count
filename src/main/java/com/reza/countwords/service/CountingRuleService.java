package com.reza.countwords.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.request.base.CountingRuleBaseRequest;

public interface CountingRuleService {

	CountingRule addRule(CountingRuleBaseRequest request);

	CountingRule findByName(String name);

	Page<CountingRule> findAll(Pageable pageable);

}