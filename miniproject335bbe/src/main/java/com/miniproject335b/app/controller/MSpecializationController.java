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

import com.miniproject335b.app.model.Mspecialization;
import com.miniproject335b.app.repository.MSpecializationRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/dokter/")
public class MSpecializationController {
	@Autowired
	MSpecializationRepository specializationRepository;

	@GetMapping("spesialis")
	public ResponseEntity<List<Mspecialization>> getAllSpesialis() {
		try {
			List<Mspecialization> listSpesialis = this.specializationRepository.findByIsDelete();
			return new ResponseEntity<>(listSpesialis, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("spesialis/{id}")
	public ResponseEntity<List<Mspecialization>> getSpesialisById(@PathVariable("id") Long id) {
		try {
			Optional<Mspecialization> spesialisasi = this.specializationRepository.findById(id);
			if (spesialisasi.isPresent()) {
				ResponseEntity rest = new ResponseEntity<>(spesialisasi, HttpStatus.OK);
				return rest;

			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return new ResponseEntity<List<Mspecialization>>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping("spesialis/add")
	public ResponseEntity<Object> saveSpesialis(@RequestBody Mspecialization specialization){
		try {
			List<Mspecialization> dataPresent = this.specializationRepository.findByName(specialization.getName());
			if(!dataPresent.isEmpty()) {
				return new ResponseEntity<>("Data Sudah Ada", HttpStatus.OK);
			}
		}catch(Exception e) {
		}
		Mspecialization dataSpesialis = this.specializationRepository.save(specialization);
		if(dataSpesialis.equals(specialization)) {
			return new ResponseEntity<>("Save Data Success", HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>("Save Failed", HttpStatus.BAD_REQUEST);
		}
		
	}

	@PutMapping("spesialis/delete/{id}")
	public ResponseEntity<Object> deleteSpesialis(@PathVariable("id") Long id) {
		Optional<Mspecialization> spesialisData = this.specializationRepository.findById(id);

		if (spesialisData.isPresent()) {
			Mspecialization spesialisasi = spesialisData.get();
			spesialisasi.setIsDelete(true);
			spesialisasi.setDeletedBy(1l);
			spesialisasi.setDeletedOn(new Date());
			this.specializationRepository.save(spesialisasi);
			return new ResponseEntity<Object>("Delete Success", HttpStatus.CREATED);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("spesialis/edit")
	public ResponseEntity<Object> editSpesialis(@RequestBody Mspecialization spesialis) {
		try {
			Long id = spesialis.getId();
			Optional<Mspecialization> spesialisData = this.specializationRepository.findById(id);

			if (spesialisData.isPresent()) {
				spesialis.setId(id);
				spesialis.setModifyBy(1l);
				spesialis.setModifyOn(new Date());
				spesialis.setCreateBy(spesialisData.get().getCreateBy());
				spesialis.setCreateOn(spesialisData.get().getCreateOn());
				this.specializationRepository.save(spesialis);
				return new ResponseEntity<Object>("Update Data Success", HttpStatus.CREATED);

			} else {
				return ResponseEntity.notFound().build();
			}

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("spesialis/search")
	public ResponseEntity<List<Mspecialization>> getSpesialisByName(@RequestParam("keyword") String keyword) {
		List<Mspecialization> listSpesialis = this.specializationRepository.findByName(keyword);
		return new ResponseEntity<>(listSpesialis, HttpStatus.OK);
	}

	@GetMapping("spesialis/paging")
	public ResponseEntity<Map<String, Object>> getAllSpesialisPages(@RequestParam(defaultValue = "5") int per_page,
			@RequestParam(defaultValue = "0") int page) {
		try {
			List<Mspecialization> spesialis = new ArrayList<>();
			Pageable pagingSort = PageRequest.of(page, per_page);
			Page<Mspecialization> pages;

			pages = this.specializationRepository.findByIsDelete(pagingSort, false);

			spesialis = pages.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("page", pages.getNumber());
			response.put("data", spesialis);
			response.put("perPages", per_page);
			response.put("total", pages.getTotalElements());
			response.put("total_pages", pages.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("spesialis/pagingSearch")
	public ResponseEntity<Map<String, Object>> getAllSpesialisPagesByName(@RequestParam("keyword") String keyword,
			@RequestParam(defaultValue = "5") int per_page, @RequestParam(defaultValue = "0") int page) {
		try {
			List<Mspecialization> spesialis = new ArrayList<>();
			Pageable pagingSort = PageRequest.of(page, per_page);
			Page<Mspecialization> pages;

			pages = this.specializationRepository.findByNamePageable(keyword, pagingSort);

			spesialis = pages.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("page", pages.getNumber());
			response.put("data", spesialis);
			response.put("perPages", per_page);
			response.put("value", keyword);
			response.put("total", pages.getTotalElements());
			response.put("total_pages", pages.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
