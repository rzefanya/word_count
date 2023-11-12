package com.reza.countwords.service;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.reza.countwords.model.InputText;

public interface InputTextService {

	InputText processText(MultipartFile file) throws IOException;

	InputText addText(MultipartFile file) throws IOException;

	InputText findByFilename(String filename);

	Page<InputText> findAll(Pageable pageable);

	InputText deleteByFilename(String name);

}