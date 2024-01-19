package com.miniproject335b.app.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.multipart.MultipartFile;

import com.miniproject335b.app.model.MBiodata;
import com.miniproject335b.app.model.MCustomer;
import com.miniproject335b.app.model.MDoctor;
import com.miniproject335b.app.repository.MBiodataRepository;
import com.miniproject335b.app.repository.MCustomerRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/biodata/")
public class MBiodataController {

	@Autowired
	MBiodataRepository mBiodataRepository;
	
	@Autowired
	MCustomerRepository mCustomerRepository;
	
	@GetMapping("show")
	public ResponseEntity<List<MBiodata>> getAllBiodata() {
		try {
			List<MBiodata> listBiodata = this.mBiodataRepository.findByIsDelete();
			return new ResponseEntity<>(listBiodata, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	// CreatedByBayu
	@GetMapping("findbiodata/maxid")
	public ResponseEntity<Long> getMaxBiodataId() {
		try {
			Long id = this.mBiodataRepository.findMaxBiodataId();
			return new ResponseEntity<>(id, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	// CreatedByBayu
	@GetMapping("search/byname")
	public ResponseEntity<List<MBiodata>> getMEducationLevelByName(@RequestParam("keyword") String keyword) {
		List<MBiodata> listBiodata = this.mBiodataRepository.searchByName(keyword);
		return new ResponseEntity<>(listBiodata, HttpStatus.OK);
	}

	@PostMapping("create/{id}")
	public ResponseEntity<Object> createBiodata(@RequestBody MBiodata bio, @PathVariable("id") Long id) {
		bio.setCreatedBy(id);
		bio.setCreatedOn(new Date());
		MBiodata saveBio = this.mBiodataRepository.save(bio);
		if (saveBio.equals(bio)) {
			return new ResponseEntity<>("Save Data Success", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Save Failed", HttpStatus.BAD_REQUEST);
		}
	}

	// CreatedByBayu
	@PostMapping("add/fullname")
	public ResponseEntity<Object> getBiodata(@RequestBody MBiodata mbiodata) {
		mbiodata.setCreatedBy((long) 1);
		mbiodata.setCreatedOn(new Date());
		mbiodata.setIsDelete(false);

		MBiodata mBiodataData = this.mBiodataRepository.save(mbiodata);
		if (mBiodataData.equals(mbiodata)) {
			return new ResponseEntity<>("Save Data Success", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Save Failed", HttpStatus.BAD_REQUEST);
		}
	}

	// CreatedByBayu
	@GetMapping("edit/{id}")
	public ResponseEntity<Optional<MBiodata>> getCustomerById(@PathVariable("id") Long id) {
		try {
			Optional<MBiodata> customerData = this.mBiodataRepository.findByIdFalse(id, false);
			return new ResponseEntity<Optional<MBiodata>>(customerData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Optional<MBiodata>>(HttpStatus.NO_CONTENT);
		}
	}
	//CreatedByBayu
	@GetMapping("delete/{id}")
	public ResponseEntity<Optional<MBiodata>> getAllBiodataById(@PathVariable("id") Long id) {
		try {
			Optional<MBiodata> mBiodata = this.mBiodataRepository.findById(id);
			if (mBiodata.isPresent()) {
				ResponseEntity rest = new ResponseEntity<>(mBiodata, HttpStatus.OK);
				return rest;
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return new ResponseEntity<Optional<MBiodata>>(HttpStatus.NO_CONTENT);
		}
	}
	// CreatedByBayu
	@PutMapping("edit/data")
	public ResponseEntity<Object> editCustomerMember(@RequestBody MBiodata mbiodata) {
		Long id = mbiodata.getId();
		Optional<MBiodata> biodataData = this.mBiodataRepository.findById(id);

		if (biodataData.isPresent()) {
			mbiodata.setId(id);
			mbiodata.setModifiedBy((long) 1);
			mbiodata.setModifiedOn(new Date());
			mbiodata.setCreatedBy(biodataData.get().getCreatedBy());
			mbiodata.setCreatedOn(biodataData.get().getCreatedOn());
			mbiodata.setIsDelete(biodataData.get().getIsDelete());
			mbiodata.setImage(biodataData.get().getImage());
			mbiodata.setImagePath(biodataData.get().getImagePath());
			mbiodata.setMobilePhone(biodataData.get().getMobilePhone());
			this.mBiodataRepository.save(mbiodata);
			return new ResponseEntity<Object>("Update Success", HttpStatus.CREATED);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	//CreatedByBayu
	@PutMapping("delete/data/{id}")
	public ResponseEntity<Object> deleteMCustomerMember(@PathVariable("id") Long id){
		try {
			Optional<MBiodata> mCustomerMemberDelete = this.mBiodataRepository.findById(id);
			if(mCustomerMemberDelete.isPresent()) {
				MBiodata mCustomerMemberData = mCustomerMemberDelete.get();
				mCustomerMemberData.setIsDelete(true);
				mCustomerMemberData.setDeletedBy((long) 1);
				mCustomerMemberData.setDeletedOn(new Date());
				
				this.mBiodataRepository.save(mCustomerMemberData);	
				return new ResponseEntity<>("Delete Data Success", HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Delete Success", HttpStatus.BAD_REQUEST);

			}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}

	@PutMapping("img/{id}")
	public ResponseEntity<Object> changeProfileImage(@RequestParam("img") MultipartFile file,
			@PathVariable("id") Long id) {
		try {
			System.out.println(file.getSize());
			System.out.println(file.getContentType().substring(0,5));
			if(file.getContentType().substring(0,5).equals("image")) {
				// Punya Handinata
				 String folderPath = "D:\\Handinata\\Mini Project\\miniproject335bfe\\src\\main\\resources\\static\\uploads\\" + file.getOriginalFilename();
				// Punya Fadli
				//String folderPath = "/home/pr001/Data/XsisAcademy/miniproject335bfe/src/main/resources/static/uploads/" + file.getOriginalFilename();
				
				File path = new File(folderPath);
				path.createNewFile();
				FileOutputStream output = new FileOutputStream(path);
				// System.out.println(file.getSize());

				output.write(file.getBytes());
				output.close();

				// 1048576

				MBiodata image = this.mBiodataRepository.findById(id).get();
				image.setModifiedBy(id);
				image.setModifiedOn(new Date());
				image.setImagePath("/uploads/" + file.getOriginalFilename());
				this.mBiodataRepository.save(image);
				return new ResponseEntity<Object>("Save Successful", HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>("Error", HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity<Object>("Save Failed", HttpStatus.NO_CONTENT);
		}

	}

	@GetMapping("{id}")
	public ResponseEntity<Object> getBiodataById(@PathVariable("id") Long id) {
		try {
			MBiodata data = this.mBiodataRepository.findById(id).get();
			return new ResponseEntity<Object>(data, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PutMapping("edit/{id}")
	public ResponseEntity<Object> editBiodata(@RequestBody MBiodata mbiodata, @PathVariable("id") Long id){
		//WARNING!!! REQUESTBODY MBIODATA INI BERISI CREATED ON, NAMUN BUKAN BERUPA TANGGAL KAPAN MBIODATA DIBUAT,
		//NAMUN TANGGAL LAHIR DARI USER
		MBiodata bio = this.mBiodataRepository.findById(mbiodata.getId()).orElse(null);
		if(bio!=null) {
			bio.setFullname(mbiodata.getFullname());
			bio.setMobilePhone(mbiodata.getMobilePhone());
			bio.setModifiedBy(id);
			bio.setModifiedOn(new Date());
			MBiodata bios = this.mBiodataRepository.save(bio);
			if(bios.equals(bio)) {
				MCustomer mc = this.mCustomerRepository.findByBiodataId(bio.getId());
				mc.setDob(mbiodata.getCreatedOn());
				mc.setModifiedBy(id);
				mc.setModifiedOn(new Date());
				MCustomer mcs = this.mCustomerRepository.save(mc);
				if(mcs.equals(mc)) {
					return new ResponseEntity<>("Modify success", HttpStatus.OK);
				}else {
					return new ResponseEntity<>("Modify failed", HttpStatus.OK);
				}
			}else {
				return new ResponseEntity<>("Modify failed", HttpStatus.OK);
			}
		}else {
			return new ResponseEntity<>("Modify failed", HttpStatus.OK);
		}
	}
}
