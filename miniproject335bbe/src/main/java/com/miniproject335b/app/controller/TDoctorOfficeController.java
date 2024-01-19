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

import com.miniproject335b.app.model.TDoctorOffice;
import com.miniproject335b.app.repository.TDoctorOfficeRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/office/")
public class TDoctorOfficeController {

	@Autowired
	public TDoctorOfficeRepository tDoctorOfficeRepository;
	
	@GetMapping("show")
	public ResponseEntity<List<TDoctorOffice>> getAllDoctorOffice() {
		try {
			List<TDoctorOffice> listOffice = this.tDoctorOfficeRepository.findByIsDelete();
			return new ResponseEntity<>(listOffice, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<List<TDoctorOffice>> getByDoctorId(@PathVariable("id") Long id) {
		try {
			List<TDoctorOffice> listData = this.tDoctorOfficeRepository.findByDoctorId(id);
			return new ResponseEntity<>(listData, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
