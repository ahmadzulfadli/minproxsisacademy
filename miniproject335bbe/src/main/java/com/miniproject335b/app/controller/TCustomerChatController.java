package com.miniproject335b.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject335b.app.repository.TCustomerChatRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/chat/")
public class TCustomerChatController {

	@Autowired
	TCustomerChatRepository repository;
	
	@GetMapping("{id}")
	public ResponseEntity<Long> getChatByDoctorId(@PathVariable("id") Long id) {
		try {
			Long jumlah = this.repository.countChatByDoctorId(id);
			return new ResponseEntity<Long>(jumlah, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
		}
	}
}
