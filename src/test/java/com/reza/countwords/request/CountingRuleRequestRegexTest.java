package com.reza.countwords.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CountingRuleRequestRegexTest {
	@Test
	public void getAttributesTest() {
		CountingRuleRequestRegex rule = new CountingRuleRequestRegex();
		rule.setRegex(".*");

		assertEquals("{\"regex\":\".*\"}", rule.getAttributes());
	}
}
