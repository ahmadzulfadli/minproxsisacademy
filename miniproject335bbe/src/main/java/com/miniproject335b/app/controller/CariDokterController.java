package com.miniproject335b.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject335b.app.model.MLocation;
import com.miniproject335b.app.repository.MLocationRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/dokter/")
public class CariDokterController {
	
	@Autowired
	public MLocationRepository lokasi;
	
	@GetMapping("lokasi")
	public ResponseEntity<List<MLocation>> getAllLocation(){
		try {
			List<MLocation> listLokasi = this.lokasi.findAll();
			return new ResponseEntity<>(listLokasi,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

}
