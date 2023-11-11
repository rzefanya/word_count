package com.reza.countwords.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reza.countwords.model.CountingRule;

public interface CountingRuleRepository extends JpaRepository<CountingRule, String> {

}
