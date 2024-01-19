package com.miniproject335b.app.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject335b.app.model.MCustomerRelation;
import com.miniproject335b.app.repository.MCustomerRelationRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class MCustomerRelationController {
	
	@Autowired
	MCustomerRelationRepository mCustomerRelationRepository;
	
	//Read	 (Get)
	@GetMapping("customerrelation")
	public ResponseEntity<List<MCustomerRelation>> getAllMCustomerRelation(){
		try {
			List<MCustomerRelation> listMCustomerRelation = this.mCustomerRelationRepository.findByIsDelete(false);
			return new ResponseEntity<>(listMCustomerRelation, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("customerrelation/{id}")
	public ResponseEntity<Optional<MCustomerRelation>> getAllMCustomerRelationById(@PathVariable ("id") Long id){
		try {
			Optional<MCustomerRelation> mCustomerRelation = this.mCustomerRelationRepository.findById(id);
			if(mCustomerRelation.isPresent()) {
				ResponseEntity rest = new ResponseEntity<>(mCustomerRelation, HttpStatus.OK);
				return rest;
			}else {
				return ResponseEntity.notFound().build();
			}
		}catch (Exception e) {
			return new ResponseEntity<Optional<MCustomerRelation>>(HttpStatus.NO_CONTENT);
		}
	}	
	//Create (Post)
	@PostMapping("customerrelation/add")
	public ResponseEntity<Object> getCustomer(@RequestBody MCustomerRelation mcustomerrelation){
		mcustomerrelation.setCreatedBy((long)1);
		mcustomerrelation.setCreatedOn(new Date());
		mcustomerrelation.setIsDelete(false);
		
		MCustomerRelation mCustomerRelationData = this.mCustomerRelationRepository.save(mcustomerrelation);
		if(mCustomerRelationData.equals(mcustomerrelation)) {
			return new ResponseEntity<>("Save Data Success", HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Save Failed", HttpStatus.BAD_REQUEST);
		}
	}
	//Update (Put)
	//Delete (Put)
}
