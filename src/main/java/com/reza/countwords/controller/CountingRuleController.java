package com.reza.countwords.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.request.CountingRuleRequestBasic;
import com.reza.countwords.request.CountingRuleRequestRegex;
import com.reza.countwords.response.ResponseWrapper;
import com.reza.countwords.service.CountingRuleService;
import com.reza.countwords.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rule")
@Slf4j
public class CountingRuleController {

	@Autowired
	private CountingRuleService service;

	@Operation(summary = "Save rule created with regex")
	@ApiResponses(value = { @ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", content = {
					@Content(schema = @Schema(implementation = ResponseWrapper.class)) }) })
	@PostMapping("/saveRegex")
	public ResponseEntity<ResponseWrapper<CountingRule>> saveRegex(@Valid @RequestBody CountingRuleRequestRegex request)
			throws MethodArgumentNotValidException {
		log.info("save request={}", request);
		return ResponseUtil.ok(service.addRule(request));
	}

	@Operation(summary = "Save rule that start with and length")
	@ApiResponses(value = { @ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", content = {
					@Content(schema = @Schema(implementation = ResponseWrapper.class)) }) })
	@PostMapping("/saveBasic")
	public ResponseEntity<ResponseWrapper<CountingRule>> saveBasic(
			@Valid @RequestBody CountingRuleRequestBasic request) {
		log.info("saveBasic request={}", request);
		return ResponseUtil.ok(service.addRule(request));
	}

	@Operation(summary = "Get rules")
	@GetMapping
	public ResponseEntity<ResponseWrapper<Page<CountingRule>>> findAll(@ParameterObject Pageable pageable) {
		log.info("findAll request={}", pageable);
		return ResponseUtil.ok(service.findAll(pageable));
	}

	@Operation(summary = "Get rule by name")
	@ApiResponses(value = { @ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(implementation = ResponseWrapper.class)) }) })
	@GetMapping("/{name}")
	public ResponseEntity<ResponseWrapper<CountingRule>> findByName(@PathVariable String name) {
		log.info("findByName request={}", name);
		return ResponseUtil.ok(service.findByName(name));
	}

	@Operation(summary = "Delete rule by name")
	@ApiResponses(value = { @ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(implementation = ResponseWrapper.class)) }) })
	@DeleteMapping("/{name}")
	public ResponseEntity<ResponseWrapper<CountingRule>> deleteByName(@PathVariable String name) {
		log.info("deleteByName request={}", name);
		return ResponseUtil.ok(service.deleteByName(name));
	}
}
