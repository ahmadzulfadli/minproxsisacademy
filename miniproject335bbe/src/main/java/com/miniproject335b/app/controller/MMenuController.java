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

import com.miniproject335b.app.model.MMenu;
import com.miniproject335b.app.model.MUser;
import com.miniproject335b.app.repository.MMenuRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/landingpage/")
public class MMenuController {
	
	@Autowired
	MMenuRepository menuRepository;
	
	
	@GetMapping("menu/{userId}")
	public ResponseEntity<List<MMenu>> getAllMenu(@PathVariable ("userId") Long id){
		try {
			List<MMenu> listMenu = this.menuRepository.findByIsDeleteMenu(id);
			return new ResponseEntity<>(listMenu,HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	//CreatedByBayu
	@GetMapping("menu/topmenu")
	public ResponseEntity<List<MMenu>> getMenuByTopMenu(){
		try {
			List<MMenu> listMMenu = this.menuRepository.findTopMenu();
			return new ResponseEntity<>(listMMenu,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	@GetMapping("menu/submenu")
	public ResponseEntity<List<MMenu>> getMenuBySubMenu(@RequestParam("id") Long id){
		try {
			List<MMenu> listMMenu = this.menuRepository.findSubMenu(id);
			return new ResponseEntity<>(listMMenu,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
