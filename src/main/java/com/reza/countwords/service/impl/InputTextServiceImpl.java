package com.reza.countwords.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reza.countwords.model.CountingRule;
import com.reza.countwords.model.InputText;
import com.reza.countwords.repository.InputTextRepository;
import com.reza.countwords.service.InputTextService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InputTextServiceImpl implements InputTextService {
	@Autowired
	private InputTextRepository repo;

	@Override
	public InputText processText(MultipartFile file) throws IOException {
		log.info("processText file={}", file.getOriginalFilename());
		InputText text = new InputText();
		text.setFilename(file.getOriginalFilename());
		text.setText(new String(file.getBytes()));
		return text;
	}

	@Override
	public InputText addText(MultipartFile file) throws IOException {
		log.info("addText file={}", file.getOriginalFilename());
		InputText text = processText(file);
		text = repo.save(text);
		return text;
	}

	@Override
	public InputText findByFilename(String filename) {
		log.info("findByFilename filename={}", filename);
		Optional<InputText> rule = repo.findById(filename);

		if (rule.isPresent()) {
			log.info("findByFilename filename={} present", filename);
			return rule.get();
		} else {
			log.info("findByFilename filename={} null", filename);
			return null;
		}
	}

	@Override
	public Page<InputText> findAll(Pageable pageable) {
		log.info("findAll pageable={}", pageable);
		return repo.findAll(pageable);
	}

	@Override
	public InputText deleteByFilename(String name) {
		log.info("deleteByName name={}", name);
		InputText text = findByFilename(name);
		if (text != null) {
			repo.delete(text);
		}
		return text;
	}
}
