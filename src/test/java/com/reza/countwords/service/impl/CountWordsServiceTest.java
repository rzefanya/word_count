package com.reza.countwords.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reza.countwords.service.CountWordsService;

@ExtendWith(MockitoExtension.class)
public class CountWordsServiceTest {
	@InjectMocks
	private CountWordsService service;

	@Test
	public void processTest() {
		List<String> output = service.process("\\b[Mm][A-z]{4,}",
				"Mind is more important than matter. "
						+ "Mindfulness is a type of meditation in which you focus on being "
						+ "intensely aware of what you're sensing and feeling in the moment, "
						+ "without interpretation or judgment");

		System.out.println(output);

		assertAll("Grouped assertion of count words", () -> assertEquals(4, output.size()),
				() -> assertEquals("matter", output.get(0)), () -> assertEquals("Mindfulness", output.get(1)),
				() -> assertEquals("meditation", output.get(2)), () -> assertEquals("moment", output.get(3)));
	}
}
