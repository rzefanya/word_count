package com.reza.countwords.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.reza.countwords.exception.ResourceNotFoundException;
import com.reza.countwords.model.InputText;
import com.reza.countwords.repository.InputTextRepository;
import com.reza.countwords.service.impl.InputTextServiceImpl;

@ExtendWith(MockitoExtension.class)
public class InputTextServiceImplTest {
	@Mock
	private InputTextRepository repo;

	@InjectMocks
	private InputTextServiceImpl service;

	@Test
	public void processTextTest() throws IOException {
		Resource fileResource = new ClassPathResource("text1.txt");
		assertNotNull(fileResource);

		MockMultipartFile file = new MockMultipartFile("file", fileResource.getFilename(),
				MediaType.MULTIPART_FORM_DATA_VALUE, fileResource.getInputStream());
		assertNotNull(file);

		InputText result = service.processText(file);

		assertAll("grouped assertion of result", () -> assertEquals("text1.txt", result.getFilename()),
				() -> assertEquals("text", result.getText()));
	}

	@Test
	public void addTextTest() throws IOException {
		when(repo.save(any())).then(var -> var.getArgument(0));

		Resource fileResource = new ClassPathResource("text1.txt");
		assertNotNull(fileResource);

		MockMultipartFile file = new MockMultipartFile("file", fileResource.getFilename(),
				MediaType.MULTIPART_FORM_DATA_VALUE, fileResource.getInputStream());
		assertNotNull(file);

		InputText result = service.addText(file);

		assertAll("grouped assertion of result", () -> assertEquals("text1.txt", result.getFilename()),
				() -> assertEquals("text", result.getText()));
		verify(repo).save(any(InputText.class));
	}

	@Test
	public void findByFilenameTest() {
		InputText text = new InputText();
		text.setFilename("text");
		when(repo.findById(any())).thenReturn(Optional.of(text));

		InputText result = service.findByFilename("text");
		assertEquals(text, result);
	}

	@Test
	public void findByNameTestNotFound() {
		when(repo.findById(any())).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.findByFilename("text"));
	}

	@Test
	public void findAllTest() {
		InputText text = new InputText();
		text.setFilename("name");

		List<InputText> texts = new ArrayList<>();
		texts.add(text);

		Page<InputText> textPage = new PageImpl<>(texts);
		when(repo.findAll(PageRequest.of(0, 20))).thenReturn(textPage);

		Page<InputText> result = service.findAll(PageRequest.of(0, 20));
		assertEquals(textPage, result);
	}

	@Test
	public void deleteByFilenameTest() {
		InputText text = new InputText();
		text.setFilename("default");
		when(repo.findById(any())).thenReturn(Optional.of(text));

		InputText result = service.deleteByFilename("default");
		assertEquals(text, result);

		verify(repo).delete(text);
	}
}
