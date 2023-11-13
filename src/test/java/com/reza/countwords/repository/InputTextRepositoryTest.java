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

import com.reza.countwords.model.InputText;

@DataJpaTest
public class InputTextRepositoryTest {
	@Autowired
	private InputTextRepository repo;

	@Test
	public void saveTest() {
		InputText text = new InputText();
		text.setFilename("filename");
		text.setText("text");
		InputText savedText = repo.save(text);
		assertAll("grouped assertion of text", () -> assertNotNull(savedText), () -> assertEquals(text, savedText));
	}

	@Test
	public void findByIdTest() {
		Optional<InputText> text = repo.findById("filename");
		assertTrue(text.isEmpty());

		InputText newText = new InputText();
		newText.setFilename("filename");
		newText.setText("text");
		repo.save(newText);

		Optional<InputText> text2 = repo.findById("filename");

		assertAll("grouped assertion of rule", () -> assertTrue(text2.isPresent()), () -> assertNotNull(text2.get()),
				() -> assertEquals(newText, text2.get()));
	}

	@Test
	public void findAllTest() {
		Page<InputText> result1 = repo.findAll(PageRequest.of(0, 10));
		assertTrue(result1.getContent().isEmpty());

		InputText newText = new InputText();
		newText.setFilename("filename");
		newText.setText("text");
		repo.save(newText);

		Page<InputText> result2 = repo.findAll(PageRequest.of(0, 10));
		assertAll("grouped assertion of result2", () -> assertFalse(result2.getContent().isEmpty()),
				() -> assertEquals(1, result2.getNumberOfElements()), () -> assertNotNull(result2.getContent().get(0)),
				() -> assertEquals(newText, result2.getContent().get(0)));
	}

	@Test
	public void deleteTest() {
		InputText newText = new InputText();
		newText.setFilename("filename");
		newText.setText("text");
		repo.save(newText);

		List<InputText> text = repo.findAll();
		assertFalse(text.isEmpty());

		repo.delete(newText);

		List<InputText> text2 = repo.findAll();
		assertTrue(text2.isEmpty());
	}
}
