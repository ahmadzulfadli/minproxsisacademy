package com.miniproject335b.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject335b.app.model.MEducationLevel;
import com.miniproject335b.app.model.MMenuRole;
import com.miniproject335b.app.repository.MMenuRoleRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class MMenuRoleController {

	@Autowired
	MMenuRoleRepository mMenuRoleRepository;

	// Read (Get)
	@GetMapping("menurole")
	public ResponseEntity<List<MMenuRole>> getAllMCustomerRelation() {
		try {
			List<MMenuRole> listMCustomer = this.mMenuRoleRepository.findByIsDelete(false);
			return new ResponseEntity<>(listMCustomer, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("menurole/{id}")
	public ResponseEntity<List<MMenuRole>> getAllMenuRoleById(@PathVariable ("id") Long id){
		try {
			List<MMenuRole> mMenuRole = this.mMenuRoleRepository.findAllMenuRoleById(id);
			System.out.println(mMenuRole);
			if(mMenuRole != null) {
				ResponseEntity rest = new ResponseEntity<>(mMenuRole, HttpStatus.OK);
				return rest;
			}else {
				return ResponseEntity.notFound().build();
			}
		}catch (Exception e) {
			return new ResponseEntity<List<MMenuRole>>(HttpStatus.NO_CONTENT);
		}
	}
	@GetMapping("menurole/all")
	public ResponseEntity<List<MMenuRole>> getMenuRoleById(@RequestParam ("id") Long id){
		System.out.println("test");
		try {
			List<MMenuRole> mMenuRole = this.mMenuRoleRepository.findMenuRoleById(id);
			System.out.println(mMenuRole);
			if(mMenuRole != null) {
				ResponseEntity rest = new ResponseEntity<>(mMenuRole, HttpStatus.OK);
				return rest;
			}else {
				return ResponseEntity.notFound().build();
			}
		}catch (Exception e) {
			return new ResponseEntity<List<MMenuRole>>(HttpStatus.NO_CONTENT);
		}
	}
	// Create (Post)
	// Update (Put)
	// Delete (Put)
}
