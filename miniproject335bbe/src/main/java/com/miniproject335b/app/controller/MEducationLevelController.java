package com.miniproject335b.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.miniproject335b.app.model.MEducationLevel;
import com.miniproject335b.app.repository.MEducationLevelRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class MEducationLevelController {
	
	@Autowired
	MEducationLevelRepository mEducationLevelRepository;
	
	//Read
	@GetMapping("jenjangpendidikan")
	public ResponseEntity<List<MEducationLevel>> getAllMEducationLevel(){
		try {
			List<MEducationLevel> listMEducationLevel = this.mEducationLevelRepository.findByIsDelete(false);
			return new ResponseEntity<>(listMEducationLevel,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("jenjangpendidikan/{id}")
	public ResponseEntity<List<MEducationLevel>> getMEducationLevelById(@PathVariable ("id") Long id){
		try {
			Optional<MEducationLevel> meducationlevel = this.mEducationLevelRepository.findById(id);
			if(meducationlevel.isPresent()) {
				ResponseEntity rest = new ResponseEntity<>(meducationlevel, HttpStatus.OK);
				return rest;
			}else {
				return ResponseEntity.notFound().build();
			}
			
		}catch(Exception e) {
			return new ResponseEntity<List<MEducationLevel>>(HttpStatus.NO_CONTENT);
		}
	}
	@GetMapping("jenjangpendidikan/search")
	public ResponseEntity<List<MEducationLevel>> getMEducationLevelByName(@RequestParam("keyword") String keyword){
		List<MEducationLevel> listMEducationLevel = this.mEducationLevelRepository.searchByName(keyword);
		return new ResponseEntity<>(listMEducationLevel, HttpStatus.OK);
	}
	//Create
	@PostMapping("jenjangpendidikan/add")
	public ResponseEntity<Map<String, Object>> saveMEducationLevel(@RequestBody MEducationLevel meducationlevel){
		try {
	        meducationlevel.setName(meducationlevel.getName().trim());
			//validation
			Map<String, Object> response = new HashMap<>();
			List<MEducationLevel>listMEducationLevel = this.mEducationLevelRepository.findByIsDelete(false);
			for(MEducationLevel educationLevel : listMEducationLevel) {
				if(educationLevel.getName().equalsIgnoreCase(meducationlevel.getName())) {
					response.put("status", "failed");
					response.put("message", "nama sudah ada");
					return new ResponseEntity<> (response,HttpStatus.OK);
				
				}
			}
			//endvalidate
				
			meducationlevel.setCreatedBy((long) 1);
			meducationlevel.setCreatedOn(new Date());
			meducationlevel.setIsDelete(false);
			
			MEducationLevel mEducationLevelData = this.mEducationLevelRepository.save(meducationlevel);
			if(mEducationLevelData.equals(meducationlevel)) {
				response.put("status", "success");
				return new ResponseEntity<>(response, HttpStatus.CREATED);
			}else {
				response.put("status", "failed");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}
	//Update
	@PutMapping("jenjangpendidikan/edit")
    public ResponseEntity<Map<String, Object>> editMEducationLevel(@RequestBody MEducationLevel meducationlevel){
        Long id = meducationlevel.getId();
        System.out.println(id);
        meducationlevel.setName(meducationlevel.getName().trim());
        //validation
        Map<String, Object> response = new HashMap<>();
		List<MEducationLevel>listMEducationLevel = this.mEducationLevelRepository.findByIsDelete(false);
		for(MEducationLevel educationLevel : listMEducationLevel) {
			if(educationLevel.getName().equalsIgnoreCase(meducationlevel.getName())) {
				response.put("status", "failed");
				response.put("message", "nama sudah ada");
				return new ResponseEntity<> (response,HttpStatus.OK);
			
			}
		}
		//endvalidate
        Optional<MEducationLevel> mEducationLevelData = this.mEducationLevelRepository.findById(id);
        if (mEducationLevelData.isPresent()) {
        	meducationlevel.setId(id);
        	meducationlevel.setModifiedBy((long)1);
        	meducationlevel.setModifiedOn(new Date());
        	meducationlevel.setCreatedBy(mEducationLevelData.get().getCreatedBy());
        	meducationlevel.setCreatedOn(mEducationLevelData.get().getCreatedOn());
        	meducationlevel.setIsDelete(mEducationLevelData.get().getIsDelete());
            this.mEducationLevelRepository.save(meducationlevel);
            response.put("status", "success");
			return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
        	response.put("status", "failed");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
	
	//Delete
	@PutMapping("jenjangpendidikan/delete/{id}")
	public ResponseEntity<Object> deleteMEducationLevel (@PathVariable("id") Long id){
		try {
			Optional<MEducationLevel> mEducationLevelDelete = this.mEducationLevelRepository.findById(id);
			if(mEducationLevelDelete.isPresent()){
				MEducationLevel mEducationLevelData = mEducationLevelDelete.get();
				mEducationLevelData.setIsDelete(true);
				mEducationLevelData.setDeletedBy((long)1);
				mEducationLevelData.setDeletedOn(new Date());
				this.mEducationLevelRepository.save(mEducationLevelData);
			
				return new ResponseEntity<>("Delete Data Success", HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Delete Failed", HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
