package com.reza.countwords.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reza.countwords.model.InputText;
import com.reza.countwords.repository.InputTextRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InputTextService {
	@Autowired
	private InputTextRepository repo;

	public InputText processText(MultipartFile file) throws IOException {
		InputText text = new InputText();
		text.setFilename(file.getOriginalFilename());
		text.setText(new String(file.getBytes()));
		return text;
	}

	public InputText addText(MultipartFile file) throws IOException {
		InputText text = processText(file);
		text = repo.save(text);
		return text;
	}

	public InputText findByFilename(String filename) {
		Optional<InputText> rule = repo.findById(filename);

		if (rule.isPresent()) {
			return rule.get();
		} else {
			return null;
		}
	}

	public Page<InputText> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}
}
