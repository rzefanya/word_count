package com.reza.countwords.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reza.countwords.model.InputText;

public interface InputTextRepository extends JpaRepository<InputText, String> {

}
