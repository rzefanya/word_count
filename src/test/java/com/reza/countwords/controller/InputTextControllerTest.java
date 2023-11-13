package com.reza.countwords.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.reza.countwords.exception.ResourceNotFoundException;
import com.reza.countwords.model.InputText;
import com.reza.countwords.service.InputTextService;

@WebMvcTest(InputTextController.class)
public class InputTextControllerTest {

	@MockBean
	private InputTextService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void uploadFileTest() throws Exception {
		InputText text = new InputText();
		text.setFilename("text1.txt");
		text.setText("text");
		when(service.addText(any())).thenReturn(text);

		Resource fileResource = new ClassPathResource("text1.txt");
		assertNotNull(fileResource);

		MockMultipartFile file = new MockMultipartFile("file", fileResource.getFilename(),
				MediaType.MULTIPART_FORM_DATA_VALUE, fileResource.getInputStream());
		assertNotNull(file);

		mockMvc.perform(multipart("/text").file(file)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.filename", is("text1.txt"))).andExpect(jsonPath("$.data.text", is("text")));
	}

	@Test
	public void findAllTest() throws Exception {
		InputText text = new InputText();
		text.setFilename("text1.txt");
		text.setText("text");

		List<InputText> texts = new ArrayList<>();
		texts.add(text);

		Page<InputText> page = new PageImpl<>(texts);
		when(service.findAll(any())).thenReturn(page);

		mockMvc.perform(get("/text").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.content[0].filename", is("text1.txt")))
				.andExpect(jsonPath("$.data.content[0]text", is("text")));
	}

	@Test
	public void findByNameTest() throws Exception {
		InputText text = new InputText();
		text.setFilename("text1.txt");
		text.setText("text");
		when(service.findByFilename(any())).thenReturn(text);

		mockMvc.perform(get("/text/text1.txt").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.filename", is("text1.txt"))).andExpect(jsonPath("$.data.text", is("text")));
	}

	@Test
	public void findByNameTestNotFound() throws Exception {
		when(service.findByFilename(any())).thenThrow(new ResourceNotFoundException("file name not found"));
		mockMvc.perform(get("/text/text1.txt").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errors[0]", is("file name not found")));
	}

	@Test
	public void deleteByFilenameTest() throws Exception {
		InputText text = new InputText();
		text.setFilename("text1.txt");
		text.setText("text");
		when(service.deleteByFilename(any())).thenReturn(text);

		mockMvc.perform(delete("/text/text1.txt").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.filename", is("text1.txt"))).andExpect(jsonPath("$.data.text", is("text")));
	}

	@Test
	public void deleteByFilenameTestNotFound() throws Exception {
		when(service.deleteByFilename(any())).thenThrow(new ResourceNotFoundException("file name not found"));
		mockMvc.perform(delete("/text/text1.txt").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.errors[0]", is("file name not found")));
	}
}
