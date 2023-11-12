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

}
