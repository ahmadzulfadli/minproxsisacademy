package com.miniproject335b.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject335b.app.model.MMedicalFacility;
import com.miniproject335b.app.repository.MMedicalFacilityRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/facility/")
public class MMedicalFacilityController {

	@Autowired
	public MMedicalFacilityRepository mMedicalFacilityRepository;
	
	@GetMapping("show")
	public ResponseEntity<List<MMedicalFacility>> getAllMedicalFacility() {
		try {
			List<MMedicalFacility> listFacility = this.mMedicalFacilityRepository.findByIsDelete();
			return new ResponseEntity<>(listFacility, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
