package com.reza.countwords.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.reza.countwords.exception.ResourceNotFoundException;
import com.reza.countwords.model.CountingRule;
import com.reza.countwords.request.CountingRuleRequestBasic;
import com.reza.countwords.request.CountingRuleRequestRegex;
import com.reza.countwords.service.CountingRuleService;

@WebMvcTest(CountingRuleController.class)
public class CountingRuleControllerTest {

	@MockBean
	private CountingRuleService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void saveRegexTest() throws Exception {
		CountingRule rule = new CountingRule();
		rule.setName("rule");

		when(service.addRule(any(CountingRuleRequestRegex.class))).thenReturn(rule);
		mockMvc.perform(post("/rule/saveRegex").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"name\": \"test\", \"regex\": \".*\" }")).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.name", is("rule")));
	}

	@Test
	public void saveRegexTestBadRequest() throws Exception {
		mockMvc.perform(post("/rule/saveRegex").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors", containsInAnyOrder("name is mandatory", "regex is mandatory")));
	}

	@Test
	public void saveBasicTest() throws Exception {
		CountingRule rule = new CountingRule();
		rule.setName("rule");

		when(service.addRule(any(CountingRuleRequestBasic.class))).thenReturn(rule);
		mockMvc.perform(post("/rule/saveBasic").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"name\": \"rule\", \"startsWith\": \"m*\", \"minimumLength\": 1 }"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.data.name", is("rule")));
	}

	@Test
	public void saveBasicTestBadRequest() throws Exception {
		CountingRule rule = new CountingRule();
		rule.setName("rule");

		when(service.addRule(any(CountingRuleRequestBasic.class))).thenReturn(rule);
		mockMvc.perform(post("/rule/saveBasic").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors", containsInAnyOrder("name is mandatory",
						"minimumLength must be more than 0", "startsWith is mandatory")));
		;
	}

	@Test
	public void findAllTest() throws Exception {
		CountingRule rule = new CountingRule();
		rule.setName("rule");

		List<CountingRule> rules = new ArrayList<>();
		rules.add(rule);

		Page<CountingRule> page = new PageImpl<>(rules);

		when(service.findAll(any())).thenReturn(page);
		mockMvc.perform(get("/rule").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.content[0].name", is("rule")));
	}

	@Test
	public void findByNameTest() throws Exception {
		CountingRule rule = new CountingRule();
		rule.setName("rule1");

		when(service.findByName(any())).thenReturn(rule);
		mockMvc.perform(get("/rule/rule1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.name", is("rule1")));
	}

	@Test
	public void findByNameTestNotFound() throws Exception {
		when(service.findByName(any())).thenThrow(new ResourceNotFoundException("rule name not found"));
		mockMvc.perform(get("/rule/rule1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errors[0]", is("rule name not found")));
	}

	@Test
	public void deleteByNameTest() throws Exception {
		CountingRule rule = new CountingRule();
		rule.setName("rule1");

		when(service.deleteByName(any())).thenReturn(rule);
		mockMvc.perform(delete("/rule/rule1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.name", is("rule1")));
	}

	@Test
	public void deleteByNameTestNotFound() throws Exception {
		when(service.deleteByName(any())).thenThrow(new ResourceNotFoundException("rule name not found"));
		mockMvc.perform(delete("/rule/rule1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errors[0]", is("rule name not found")));
	}
}
