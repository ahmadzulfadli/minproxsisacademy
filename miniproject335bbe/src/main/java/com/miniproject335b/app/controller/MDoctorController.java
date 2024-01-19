package com.miniproject335b.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject335b.app.model.MDoctor;
import com.miniproject335b.app.repository.MDoctorRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/doctor/")
public class MDoctorController {

	@Autowired
	MDoctorRepository mDoctorRepository;

	@GetMapping("show")
	public ResponseEntity<List<MDoctor>> findAllDoctor() {
		try {
			List<MDoctor> listDoctor = this.mDoctorRepository.findByIsDelete();
			return new ResponseEntity<>(listDoctor, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}

	@GetMapping("{id}")
	public ResponseEntity<Object> getDoctorById(@PathVariable("id") Long id) {
		try {
			MDoctor doctor = this.mDoctorRepository.findByDoctorId(id);
			return new ResponseEntity<Object>(doctor, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	/*
	 * @GetMapping("caridokter") public ResponseEntity<Object> getDoctorById() { try
	 * { List<Object> doctor = this.mDoctorRepository.listCariDoctor();
	 * System.out.println(doctor); return new ResponseEntity<>(doctor,
	 * HttpStatus.OK); } catch(Exception e) { return new
	 * ResponseEntity<>(HttpStatus.NO_CONTENT); } }
	 */

	@GetMapping("pagingsearch")
	public ResponseEntity<Map<String, Object>> getAllDoctorBySearch(@RequestParam("namaDokter") String namaDokter,
			@RequestParam("namaSpesialis") String namaSpesialis, @RequestParam("namaTindakan") String namaTindakan,
			@RequestParam("namaLokasi") String namaLokasi, @RequestParam(defaultValue = "5") int per_page,
			@RequestParam(defaultValue = "0") int page) {
		try {
			
			List<String> keydata = new ArrayList<>();
			keydata.add("dokterId");
			keydata.add("fullName");
			keydata.add("namaMedicalFacility");
			keydata.add("namaSpesialis");
			keydata.add("namaTindakan");
			keydata.add("namaKota");
			keydata.add("parentId");
			keydata.add("namaKecamatan");
			keydata.add("pengalaman");
			keydata.add("jenisRumahSakit");
			keydata.add("fotoUrl");
			keydata.add("hariBuka");
			
		
			List<Object> dokter = new ArrayList<>();

			Pageable pagingSort = PageRequest.of(page, per_page);
			Page<Object> pages;

			pages = this.mDoctorRepository.findBySearch(namaDokter, namaSpesialis, namaTindakan, namaLokasi, pagingSort);
			System.out.println(pages);

			dokter = pages.getContent();
			
			List<Map<String, Object>> dokterResponse = new ArrayList<>();

			for (Object dokterObj : dokter) {
			    Map<String, Object> dokterMap = new HashMap<>();

			    Object[] dokterArray = (Object[]) dokterObj;

			    for (int i = 0; i < keydata.size(); i++) {
			        dokterMap.put(keydata.get(i), dokterArray[i]);
			    }

			    dokterResponse.add(dokterMap);
			}

			Map<String, Object> response = new HashMap<>();
			response.put("page", pages.getNumber());
			response.put("data", dokterResponse);
			response.put("perPages", per_page);
			response.put("value", namaDokter);
			response.put("value2", namaSpesialis);
			response.put("value3", namaTindakan);
			response.put("value4", namaLokasi);
			response.put("total", pages.getTotalElements());
			response.put("total_pages", pages.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
