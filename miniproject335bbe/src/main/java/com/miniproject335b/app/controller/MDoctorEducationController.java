package com.miniproject335b.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject335b.app.model.MDoctorEducation;
import com.miniproject335b.app.repository.MDoctorEducationRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/education/")
public class MDoctorEducationController {
	
	@Autowired
	MDoctorEducationRepository mDoctorEducationRepository;

	@GetMapping("{id}")
	public ResponseEntity<List<MDoctorEducation>> getAllDoctorEducation(@PathVariable("id") Long id) {
		try {
			List<MDoctorEducation> listEducation = this.mDoctorEducationRepository.findByDoctorId(id);
			return new ResponseEntity<>(listEducation, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
