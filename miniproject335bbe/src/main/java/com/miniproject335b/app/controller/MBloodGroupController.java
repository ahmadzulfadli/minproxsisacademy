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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject335b.app.model.MBloodGroup;
import com.miniproject335b.app.repository.MBloodGroupRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class MBloodGroupController {

	@Autowired
	MBloodGroupRepository mBloodGroupRepository;

	// Read (Get)
	@GetMapping("bloodgroup")
	public ResponseEntity<List<MBloodGroup>> getAllMBloodGroup() {
		try {
			List<MBloodGroup> listMBloodGroup = this.mBloodGroupRepository.findByIsDelete(false);
			return new ResponseEntity<>(listMBloodGroup, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("get/{id}")
	public ResponseEntity<Object> getBloddById(@PathVariable("id") Long id) {
		try {
			MBloodGroup data = this.mBloodGroupRepository.findById(id).get();
			return new ResponseEntity<Object>(data, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
	}

	// Create (Post)
	@PostMapping("blood/add")
	public ResponseEntity<Object> getMBloodGroup(@RequestBody MBloodGroup mbloodgroup) {
		
		Optional<MBloodGroup> data = this.mBloodGroupRepository.findByCode(mbloodgroup.getCode().toUpperCase());
		System.out.println(mbloodgroup.getCode().toUpperCase());
		if(data.isPresent()) {
			return new ResponseEntity<>("Data Sudah Ada", HttpStatus.OK); 
		} 
		
		mbloodgroup.setCreatedBy((long) 1);
		mbloodgroup.setCreatedOn(new Date());
		mbloodgroup.setIsDelete(false);
		mbloodgroup.setCode(mbloodgroup.getCode().toUpperCase());

		MBloodGroup mBloodGroupData = this.mBloodGroupRepository.save(mbloodgroup);
		if (mBloodGroupData.equals(mbloodgroup)) {
			return new ResponseEntity<>("Save Data Success", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Save Failed", HttpStatus.BAD_REQUEST);

		}
	}

	// Update (Put)
	@PutMapping("blood/edit/{userId}")
	public ResponseEntity<Object> editBlood(@RequestBody MBloodGroup blood, @PathVariable("userId") Long userId) {
		try {
			Optional<MBloodGroup> data = this.mBloodGroupRepository.findByCode(blood.getCode().toUpperCase());
			//System.out.println(blood.getCode().toUpperCase());
			System.out.println(data.get().getCode());
			if(data.isPresent()) {
				if(data.get().getCode().toUpperCase().equals(blood.getCode().toUpperCase()) && data.get().getId() != blood.getId()) {
					return new ResponseEntity<>("Data Sudah Ada", HttpStatus.OK); 
				}
				
				
			} 	
		} catch (Exception e){
			
		}
		
		Long bloodId = blood.getId();
		Optional<MBloodGroup> item = this.mBloodGroupRepository.findById(bloodId);

		System.out.println(item);

		if (item.isPresent()) {
			blood.setId(bloodId);
			blood.setModifiedBy(userId);
			blood.setModifiedOn(new Date());
			blood.setCreatedBy(item.get().getCreatedBy());
			blood.setCreatedOn(item.get().getCreatedOn());
			blood.setIsDelete(false);
			blood.setCode(blood.getCode().toUpperCase());
			this.mBloodGroupRepository.save(blood);

			return new ResponseEntity<Object>("Edit Success", HttpStatus.CREATED);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	// Delete (Put)
	@PutMapping("blood/delete/{userId}/{id}")
	public ResponseEntity<Object> deleteBlood(@PathVariable("userId") Long userId, @PathVariable("id") Long id) {
		Optional<MBloodGroup> item = this.mBloodGroupRepository.findById(id);

		if (item.isPresent()) {
			MBloodGroup blood = item.get();
			blood.setDeletedBy(userId);
			blood.setDeletedOn(new Date());
			blood.setIsDelete(true);
			this.mBloodGroupRepository.save(blood);
			return new ResponseEntity<Object>("Delete Successful", HttpStatus.CREATED);
		} else {
			return ResponseEntity.notFound().build();
		}

	}
	
	@GetMapping("blood/search")
	public ResponseEntity<List<MBloodGroup>> getBySearch(@RequestParam("keyword") String keyword) {
		try {
			List<MBloodGroup> listMBloodGroup = this.mBloodGroupRepository.findBySearch(keyword);
			return new ResponseEntity<>(listMBloodGroup, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
