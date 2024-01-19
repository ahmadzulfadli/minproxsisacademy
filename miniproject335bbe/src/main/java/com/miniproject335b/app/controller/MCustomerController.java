package com.miniproject335b.app.controller;

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
import org.springframework.data.domain.Sort;
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

import com.miniproject335b.app.model.MCustomer;
import com.miniproject335b.app.repository.MCustomerRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class MCustomerController {

	@Autowired
	MCustomerRepository mCustomerRepository;

	// Read (Get)
	@GetMapping("customer")
	public ResponseEntity<List<MCustomer>> getAllMCustomerRelation() {
		try {
			List<MCustomer> listMCustomer = this.mCustomerRepository.findByIsDelete(false);
			return new ResponseEntity<>(listMCustomer, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("customer/{id}")
	public ResponseEntity<Optional<MCustomer>> getAllMCustomerRelationById(@PathVariable("id") Long id) {
		try {
			Optional<MCustomer> mCustomer = this.mCustomerRepository.findById(id);
			if (mCustomer.isPresent()) {
				ResponseEntity rest = new ResponseEntity<>(mCustomer, HttpStatus.OK);
				return rest;
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return new ResponseEntity<Optional<MCustomer>>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("customer/maxid")
	public ResponseEntity<Long> getMaxCustomerId() {
		try {
			Long id = this.mCustomerRepository.getMaxCustomerId();
			return new ResponseEntity<>(id, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		}
	}

	// Customer
	@GetMapping("customer/pasien")
	public ResponseEntity<List<Object>> getAllPasien() {
		List<Object> listJoinData = this.mCustomerRepository.findAllPasien();
		List<Object> pasienData = new ArrayList();
		for (int i = 0; i < listJoinData.size(); i++) {
			Map<String, Object> pasien = new HashMap();
			Object[] pasienDataList = Object[].class.cast(listJoinData.get(i));
			pasien.put("fullname", pasienDataList[0]);
			pasien.put("dob", pasienDataList[1]);
			pasien.put("gender", pasienDataList[2]);
			pasien.put("blood_type", pasienDataList[3]);
			pasien.put("rhesus_type", pasienDataList[4]);
			pasien.put("height", pasienDataList[5]);
			pasien.put("weight", pasienDataList[6]);
			pasien.put("relation_name", pasienDataList[7]);
			pasienData.add(pasien);
		}

		// return new ResponseEntity<>(pasienData, HttpStatus.OK);
		return new ResponseEntity<>(pasienData, HttpStatus.OK);
	}

	// Paging
//	@GetMapping("customer/paging")
//	public ResponseEntity<Map<String, Object>> getMCustomerPageable(
//			@RequestParam(defaultValue = "5") Integer per_page,
//			@RequestParam(defaultValue = "0") Integer page){
//		try {
//			List<Object> mcustomer = new ArrayList();
//			Pageable pagingSort = PageRequest.of(page, per_page);
//			Page<Object> pages; 
//			pages = this.mCustomerRepository.findByIsDelete(pagingSort,false);
//			mcustomer = pages.getContent();
//			
//			Map<String, Object> response = new HashMap();
//			response.put("page", pages.getNumber()+1);
//			response.put("data", mcustomer);
//			response.put("per_pages", per_page);
//			response.put("total", pages.getTotalElements());
//			response.put("total_pages", pages.getTotalPages());
//			return new ResponseEntity<>(response, HttpStatus.OK);
//			
//		}catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//				
//	}
//	@GetMapping("customer/paging")
//	public ResponseEntity<Map<String, Object>> getMCustomerPaging(
//			@RequestParam(defaultValue = "5") int per_page,
//			@RequestParam(defaultValue = "0") int page){
//		try {
//			List<MCustomer> mcustomer = new ArrayList();
//			Pageable pagingSort = PageRequest.of(page, per_page);
//			Page<MCustomer> pages;
//			pages = this.mCustomerRepository.findByIsDelete(pagingSort, false);
//			mcustomer = pages.getContent();
//			
//			Map<String, Object> response = new HashMap();
//			response.put("page", pages.getNumber()+1);
//			response.put("data", mcustomer);
//			response.put("per_pages", per_page);
//			response.put("total", pages.getTotalElements());
//			response.put("total_pages", pages.getTotalPages());
//			
//			return new ResponseEntity<>(response, HttpStatus.OK);
//			
//		}catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	// SortByName
	@GetMapping("customer/sort")
	public ResponseEntity<List<Object>> sortByName(@RequestParam(defaultValue = "0") Integer pageNumber,
			@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "name") String sort) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sort));

		System.out.println(sort);

		Page<Object> page = this.mCustomerRepository.sortByName(pageable);
		List<Object> mCustomers = page.getContent();

		return new ResponseEntity<>(mCustomers, HttpStatus.OK);

	}

	// Create (Post)
	@PostMapping("customer/add")
	public ResponseEntity<Object> getCustomer(@RequestBody MCustomer mcustomer) {
		mcustomer.setCreatedBy((long) 1);
		mcustomer.setCreatedOn(new Date());
		mcustomer.setIsDelete(false);

		MCustomer mCustomerData = this.mCustomerRepository.save(mcustomer);
		if (mCustomerData.equals(mcustomer)) {
			return new ResponseEntity<>("Save Data Success", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Save Failed", HttpStatus.BAD_REQUEST);
		}
	}

	// Update (Put)
	@PutMapping("customer/edit")
	public ResponseEntity<Object> editCustomerMember(@RequestBody MCustomer mcustomer) {
		Long id = mcustomer.getId();
		Optional<MCustomer> customerData = this.mCustomerRepository.findById(id);

		if (customerData.isPresent()) {
			mcustomer.setId(id);
			mcustomer.setModifiedBy((long) 1);
			mcustomer.setModifiedOn(new Date());
			mcustomer.setCreatedBy(customerData.get().getCreatedBy());
			mcustomer.setCreatedOn(customerData.get().getCreatedOn());
			mcustomer.setIsDelete(customerData.get().getIsDelete());
			mcustomer.setBiodataId(customerData.get().getBiodataId());
			this.mCustomerRepository.save(mcustomer);
			return new ResponseEntity<Object>("Update Success", HttpStatus.CREATED);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	// Delete (Put)
	@PutMapping("customer/delete/{id}")
	public ResponseEntity<Object> deleteMCustomer (@PathVariable("id") Long id){
		try {
			Optional<MCustomer> mCustomerDelete = this.mCustomerRepository.findById(id);
			if(mCustomerDelete.isPresent()){
				MCustomer mCustomerData = mCustomerDelete.get();
				mCustomerData.setIsDelete(true);
				mCustomerData.setDeletedBy((long)1);
				mCustomerData.setDeletedOn(new Date());
				this.mCustomerRepository.save(mCustomerData);
			
				return new ResponseEntity<>("Delete Data Success", HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Delete Failed", HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	// Update (Put)
	// Delete (Put)
	@GetMapping("customer/bio/{bio}")
	public ResponseEntity<Optional<MCustomer>> getMCustomerByBiodataId(@PathVariable("bio") Long bio) {
		try {
			MCustomer mCustomer = this.mCustomerRepository.findByBiodataId(bio);
			if (mCustomer!=null) {
				ResponseEntity rest = new ResponseEntity<>(mCustomer, HttpStatus.OK);
				return rest;
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return new ResponseEntity<Optional<MCustomer>>(HttpStatus.NO_CONTENT);
		}
	}
}
