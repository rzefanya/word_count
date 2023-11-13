package com.reza.countwords.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.reza.countwords.exception.ResourceNotFoundException;
import com.reza.countwords.model.CountingRule;
import com.reza.countwords.repository.CountingRuleRepository;
import com.reza.countwords.request.CountingRuleRequestBasic;
import com.reza.countwords.request.CountingRuleRequestRegex;
import com.reza.countwords.service.impl.CountingRuleServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CountingRuleServiceImplTest {
	@Mock
	private CountingRuleRepository repo;

	@InjectMocks
	private CountingRuleServiceImpl service;

	@Test
	public void addRuleBasicTest() {
		when(repo.save(any())).then(var -> var.getArgument(0));

		CountingRuleRequestBasic rule = new CountingRuleRequestBasic();
		rule.setName("rule");
		rule.setMinimumLength(10);
		rule.setStartsWith("a");
		CountingRule result = service.addRule(rule);

		assertAll("grouped assertion of result", () -> assertEquals("rule", result.getName()),
				() -> assertEquals("startLimitExtractorProcessor", result.getProcessor()),
				() -> assertEquals("{\"minimumLength\":10,\"startsWith\":\"a\"}", result.getAttributes()));
		verify(repo).save(any(CountingRule.class));
	}

	@Test
	public void addRuleRegexTest() {
		when(repo.save(any())).then(var -> var.getArgument(0));

		CountingRuleRequestRegex rule = new CountingRuleRequestRegex();
		rule.setName("rule");
		rule.setRegex(".*");
		CountingRule result = service.addRule(rule);

		assertAll("grouped assertion of result", () -> assertEquals("rule", result.getName()),
				() -> assertEquals("regexExtractorProcessor", result.getProcessor()),
				() -> assertEquals("{\"regex\":\".*\"}", result.getAttributes()));
		verify(repo).save(any(CountingRule.class));
	}

	@Test
	public void findByNameTest() {
		CountingRule rule = new CountingRule();
		rule.setName("default");
		when(repo.findById(any())).thenReturn(Optional.of(rule));

		CountingRule result = service.findByName("rule");
		assertEquals(rule, result);
	}

	@Test
	public void findByNameTestNotFound() {
		when(repo.findById(any())).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.findByName("rule"));
	}

	@Test
	public void findAllTest() {
		CountingRule rule = new CountingRule();
		rule.setName("name");

		List<CountingRule> rules = new ArrayList<>();
		rules.add(rule);

		Page<CountingRule> rulePage = new PageImpl<>(rules);
		when(repo.findAll(PageRequest.of(0, 20))).thenReturn(rulePage);

		Page<CountingRule> result = service.findAll(PageRequest.of(0, 20));
		assertEquals(rulePage, result);
	}

	@Test
	public void deleteByNameTest() {
		CountingRule rule = new CountingRule();
		rule.setName("default");
		when(repo.findById(any())).thenReturn(Optional.of(rule));

		CountingRule result = service.deleteByName("rule");
		assertEquals(rule, result);

		verify(repo).delete(rule);
	}
}
