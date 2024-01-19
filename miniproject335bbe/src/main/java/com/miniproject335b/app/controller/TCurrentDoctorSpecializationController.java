package com.miniproject335b.app.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject335b.app.model.TCurrentDoctorSpecialization;
import com.miniproject335b.app.repository.TCurrentDoctorSpecializationRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/current/")
public class TCurrentDoctorSpecializationController {

	@Autowired
	TCurrentDoctorSpecializationRepository repository;

	@GetMapping("{id}")
	public ResponseEntity<TCurrentDoctorSpecialization> getSpecialization(@PathVariable("id") Long id){
		try {
			TCurrentDoctorSpecialization current = this.repository.findByDoctorId(id);
			ResponseEntity rest = new ResponseEntity<> (current, HttpStatus.OK);
			return rest;
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping("add")
	public ResponseEntity<Object> addSpecialization (@RequestBody TCurrentDoctorSpecialization current) {
		current.setCreateBy(current.getDoctorId());
		current.setCreateOn(new Date());
		
		TCurrentDoctorSpecialization special = this.repository.save(current);
		if(special.equals(current)) {
			return new ResponseEntity<Object>("Save Success", HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Save Failed", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("edit")
	public ResponseEntity<Object> editSpecialization(@RequestBody TCurrentDoctorSpecialization current){
		Long id = current.getDoctorId();
		TCurrentDoctorSpecialization productData = this.repository.findByDoctorId(id);
		
		if(productData != null) {
			current.setId(productData.getId());
			current.setDoctorId(id);
			current.setCreateBy(productData.getCreateBy());
			current.setCreateOn(productData.getCreateOn());
			current.setModifyBy(id);
			current.setModifyOn(new Date());;
			this.repository.save(current);
			return new ResponseEntity<Object>("Update Successful", HttpStatus.CREATED);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
}
