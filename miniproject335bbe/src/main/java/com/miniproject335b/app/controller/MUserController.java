package com.miniproject335b.app.controller;

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

import com.miniproject335b.app.mail.MailChecker;
import com.miniproject335b.app.model.MAdmin;
import com.miniproject335b.app.model.MBiodata;
import com.miniproject335b.app.model.MCustomer;
import com.miniproject335b.app.model.MDoctor;
import com.miniproject335b.app.model.MRole;
import com.miniproject335b.app.model.MUser;
import com.miniproject335b.app.model.SignUpHolder;
import com.miniproject335b.app.repository.MAdminRepository;
import com.miniproject335b.app.repository.MBiodataRepository;
import com.miniproject335b.app.repository.MCustomerRepository;
import com.miniproject335b.app.repository.MDoctorRepository;
import com.miniproject335b.app.repository.MRoleRepository;
import com.miniproject335b.app.repository.MUserRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/user/")
public class MUserController {
	@Autowired
	MUserRepository mUserRepository;
	
	@Autowired
	MBiodataRepository mBiodataRepository;
	
	@Autowired
	MBiodataController mBiodataController;
	
	@Autowired
	MRoleRepository mRoleRepository;
	@Autowired
	MCustomerRepository mCustomerRepository;
	@Autowired
	MDoctorRepository mDoctorRepository;
	@Autowired
	MAdminRepository mAdminRepository;
	
	public String checkMyEmail(String email) {
		if(!MailChecker.ValidateMail(email)) {
			return "invalid";
		}
		try {
			List<MUser> checkExist = this.mUserRepository.findActiveByEmail(email);
			if(!checkExist.isEmpty()) {
				return "exist";
			}else {
				return "not exist";
			}
			
		}catch(Exception e) {
			return "not exist";
		}
	}
	
	@PutMapping("check/email")
	public ResponseEntity<Object> CheckEmail(@RequestBody MUser user){
		String ec = checkMyEmail(user.getEmail());
		if(ec.equals("invalid")) {
			return new ResponseEntity<>("invalid", HttpStatus.OK);
		}else if(ec.equals("exist")) {
			return new ResponseEntity<>("exist", HttpStatus.OK);
		}else {
			return new ResponseEntity<>("not exist", HttpStatus.OK);
		}
	}
	
	@PostMapping("create")
	public ResponseEntity<Map<String, Object>> CreateUser(@RequestBody MUser user){
		Map<String, Object> response = new HashMap();
		String ec = checkMyEmail(user.getEmail());
		if(ec.equals("invalid")) {
			response.put("status", "Invalid Email");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}else if(ec.equals("exist")) {
			response.put("status", "Email Exist");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		//Make the email null, email should be added later instead of now in case of cancelling
		String uEmail = user.getEmail();
		user.setEmail(null);
		
		user.setIsDelete(false);
		user.setIsLocked(false);
		user.setCreatedOn(new Date());
		try {
			Long maxId = this.mUserRepository.maxId();
			user.setCreatedBy(maxId+1);
		}catch(Exception e) {
			user.setCreatedBy(1l);
		}
		MUser mUser = this.mUserRepository.save(user);
		if(mUser.equals(user)) {
			response.put("status", "Add User Success");
			response.put("id", mUser.getId());
			response.put("email", uEmail);
			return new ResponseEntity<>(response,HttpStatus.CREATED);
		}else {
			response.put("status", "Add Failed");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("edit")
	public ResponseEntity<Map<String, Object>> EditPassword(@RequestBody MUser user){
		Map<String, Object> response = new HashMap();
		String ec = checkMyEmail(user.getEmail());
		if(ec.equals("invalid")) {
			response.put("status", "Invalid Email");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}else if(ec.equals("not exist")) {
			response.put("status", "Email Not Found");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		//Make the email null, email should be added later instead of now in case of cancelling
		String uEmail = user.getEmail();
		MUser data = this.mUserRepository.findByEmail(uEmail);
		data.setModifiedOn(new Date());
		data.setModifiedBy(data.getId());
		
		MUser mUser = this.mUserRepository.save(data);
		if(mUser.equals(data)) {
			response.put("status", "Edit User Success");
			response.put("id", mUser.getId());
			response.put("email", uEmail);
			return new ResponseEntity<>(response,HttpStatus.CREATED);
		}else {
			response.put("status", "Edit Failed");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("password")
	public ResponseEntity<Object> putPassword(@RequestBody MUser user){
		MUser u = this.mUserRepository.findById(user.getId()).orElse(null);
		if(u==null) {
			return new ResponseEntity<>("No User", HttpStatus.OK);
		}else {
			//Masukkan password
			u.setPassword(user.getPassword());
			u.setModifiedBy(u.getId());
			u.setModifiedOn(new Date());
			MUser saveU = this.mUserRepository.save(u);
			return new ResponseEntity<>("Password Saved", HttpStatus.CREATED);
		}
	}
	@PutMapping("role")
	public ResponseEntity<Object> putRole(@RequestBody MUser user){
		MUser u = this.mUserRepository.findById(user.getId()).orElse(null);
		if(u==null) {
			return new ResponseEntity<>("No User", HttpStatus.OK);
		}else {
			//Masukkan role
			u.setRoleId(user.getRoleId());
			u.setModifiedBy(u.getId());
			u.setModifiedOn(new Date());
			MUser saveU = this.mUserRepository.save(u);
			return new ResponseEntity<>("Role Saved", HttpStatus.CREATED);
		}
	}
	
	@PutMapping("email")
	public ResponseEntity<Object> putEmail(@RequestBody MUser user){
		MUser u = this.mUserRepository.findById(user.getId()).orElse(null);
		if(u==null) {
			return new ResponseEntity<>("No User", HttpStatus.OK);
		}else {
			//Masukkan email
			u.setEmail(user.getEmail());
			u.setModifiedBy(u.getId());
			u.setModifiedOn(new Date());
			MUser saveU = this.mUserRepository.save(u);
			return new ResponseEntity<>("Email Saved", HttpStatus.CREATED);
		}
	}
	
	@PutMapping("signup")
	public ResponseEntity<Object> signUp(@RequestBody SignUpHolder suh){
		MUser u = this.mUserRepository.findById(suh.getUser().getId()).orElse(null);
		if(u==null) {
			return new ResponseEntity<>("No User", HttpStatus.OK);
		}else {
			//Masukkan biodata
			//ResponseEntity<Object> sbio = this.mBiodataController.createBiodata(suh.getBiodata(), u.getId());
			MBiodata bio = suh.getBiodata();
			bio.setCreatedBy(u.getId());
			bio.setCreatedOn(new Date());
			MBiodata saveBio = this.mBiodataRepository.save(bio);
			if(!saveBio.equals(bio)) {
				return new ResponseEntity<>("Save Failed", HttpStatus.BAD_REQUEST);
			}
			
			//Masukkan role
			u.setRoleId(suh.getUser().getRoleId());
			u.setEmail(suh.getUser().getEmail());
			u.setBiodataId(saveBio.getId());
			u.setModifiedBy(u.getId());
			u.setModifiedOn(new Date());
			MUser saveU = this.mUserRepository.save(u);
			
			//Masukkan macam-macam dgn biodataId
			MRole getRole = this.mRoleRepository.findById(u.getRoleId()).orElse(null);
			if(getRole==null) {
			}else if(getRole.getCode().equals("ROLE_PASIEN")) {
				MCustomer pasien = new MCustomer();
				pasien.setBiodataId(u.getBiodataId());
				pasien.setCreatedBy(u.getId());
				pasien.setCreatedOn(new Date());
				pasien.setIsDelete(false);
				MCustomer pasienS = this.mCustomerRepository.save(pasien);
				if(!pasienS.equals(pasien)) {
					return new ResponseEntity<>("Save Failed", HttpStatus.BAD_REQUEST);
				}
			}else if (getRole.getCode().equals("ROLE_DOKTER")) {
				MDoctor dokter = new MDoctor();
				dokter.setBiodataId(u.getBiodataId());
				dokter.setCreatedBy(u.getId());
				dokter.setCreatedOn(new Date());
				MDoctor dokterS = this.mDoctorRepository.save(dokter);
				if(!dokterS.equals(dokter)) {
					return new ResponseEntity<>("Save Failed", HttpStatus.BAD_REQUEST);
				}
			}else if (getRole.getCode().equals("ROLE_ADMIN")) {
				MAdmin admin = new MAdmin();
				admin.setCreatedBy(u.getId());
				admin.setCreatedOn(new Date());
				admin.setBiodataId(u.getBiodataId());
				MAdmin adminS = this.mAdminRepository.save(admin);
				if(!adminS.equals(admin)) {
					return new ResponseEntity<>("Save Failed", HttpStatus.BAD_REQUEST);
				}
			}else {
				//return new ResponseEntity<>("Missed?", HttpStatus.CREATED);
			}
			//return
			return new ResponseEntity<>("Saved", HttpStatus.CREATED);
		}
	}
	
	@PostMapping("login")
	public ResponseEntity<Object> userLogin(@RequestBody MUser user){
		List<MUser> us = this.mUserRepository.findActiveByEmail(user.getEmail());
		Map<String, Object> res = new HashMap();
		if(us.isEmpty()) {
			res.put("status","Email not found");
			return new ResponseEntity<>(res, HttpStatus.OK);
		}else {
			MUser u = us.get(0);
			if(u.getPassword()==null) {
				res.put("status","Password is Empty");
				return new ResponseEntity<>(res, HttpStatus.OK);
			}
			if(u.getIsLocked()) {
				res.put("status","Account Locked");
				return new ResponseEntity<>(res, HttpStatus.OK);
			}
			if(u.getPassword().equals(user.getPassword())) {
				u.setLastLogin(new Date());
				u.setModifiedBy(u.getId());
				u.setModifiedOn(new Date());
				u.setLoginAttempt(0);
				this.mUserRepository.save(u);
				res.put("status","Success");
				res.put("id", u.getId());
				res.put("name", u.getmBiodata().getFullname());
				res.put("role", u.getRoleId());
				return new ResponseEntity<>(res, HttpStatus.OK);
			}else {
				u.setLoginAttempt(u.getLoginAttempt()+1);
				if(u.getLoginAttempt()>=3) {
					u.setIsLocked(true);
				}
				u.setModifiedBy(u.getId());
				u.setModifiedOn(new Date());
				this.mUserRepository.save(u);
				if(u.getLoginAttempt()>=3) {
					res.put("status","Wrong Password Locked");
					return new ResponseEntity<>(res, HttpStatus.OK);
				}
				res.put("status","Wrong Password");
				return new ResponseEntity<>(res, HttpStatus.OK);
			}
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> getUserById(@PathVariable("id") Long id){
		try {
			MUser u = this.mUserRepository.findById(id).get();
			//System.out.println(u);
			return new ResponseEntity<>(u, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
}
