package com.reza.countwords.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CountingRuleRequestBasicTest {
	@Test
	public void getAttributesTest() {
		CountingRuleRequestBasic rule = new CountingRuleRequestBasic();
		rule.setMinimumLength(10);
		rule.setStartsWith("a");

		assertEquals("{\"minimumLength\":10,\"startsWith\":\"a\"}", rule.getAttributes());
	}
}
