package com.reza.countwords.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.reza.countwords.model.CountingRule;

@DataJpaTest
public class CountingRuleRepositoryTest {
	@Autowired
	private CountingRuleRepository repo;

	@Test
	public void saveTest() {
		CountingRule rule = new CountingRule();
		rule.setAttributes("attribute");
		rule.setDescription("description");
		rule.setName("name");
		rule.setProcessor("processor");
		CountingRule savedRule = repo.save(rule);
		assertAll("grouped assertion of rule", () -> assertNotNull(savedRule), () -> assertEquals(rule, savedRule));
	}

	@Test
	public void findByIdTest() {
		Optional<CountingRule> rule = repo.findById("name");
		assertTrue(rule.isEmpty());

		CountingRule newRule = new CountingRule();
		newRule.setAttributes("attribute");
		newRule.setDescription("description");
		newRule.setName("name");
		newRule.setProcessor("processor");
		repo.save(newRule);

		Optional<CountingRule> rule2 = repo.findById("name");

		assertAll("grouped assertion of rule", () -> assertTrue(rule2.isPresent()), () -> assertNotNull(rule2.get()),
				() -> assertEquals(newRule, rule2.get()));
	}

	@Test
	public void findAllTest() {
		Page<CountingRule> result1 = repo.findAll(PageRequest.of(0, 10));
		assertTrue(result1.getContent().isEmpty());

		CountingRule newRule = new CountingRule();
		newRule.setAttributes("attribute");
		newRule.setDescription("description");
		newRule.setName("name");
		newRule.setProcessor("processor");
		repo.save(newRule);

		Page<CountingRule> result2 = repo.findAll(PageRequest.of(0, 10));
		assertAll("grouped assertion of result2", () -> assertFalse(result2.getContent().isEmpty()),
				() -> assertEquals(1, result2.getNumberOfElements()), () -> assertNotNull(result2.getContent().get(0)),
				() -> assertEquals(newRule, result2.getContent().get(0)));
	}

	@Test
	public void deleteTest() {
		CountingRule newRule = new CountingRule();
		newRule.setAttributes("attribute");
		newRule.setDescription("description");
		newRule.setName("name");
		newRule.setProcessor("processor");
		repo.save(newRule);

		List<CountingRule> rule = repo.findAll();
		assertFalse(rule.isEmpty());

		repo.delete(newRule);

		List<CountingRule> rule2 = repo.findAll();
		assertTrue(rule2.isEmpty());
	}
}
