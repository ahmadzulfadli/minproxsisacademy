package com.miniproject335b.app.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject335b.app.mail.EmailSenderService;
import com.miniproject335b.app.model.MUser;
import com.miniproject335b.app.model.TToken;
import com.miniproject335b.app.repository.MUserRepository;
import com.miniproject335b.app.repository.TTokenRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/token/")
public class TTokenController {
	@Autowired
	TaskScheduler taskScheduler;
	
	@Autowired
	TTokenRepository tTokenRepository;
	
	@Autowired
	EmailSenderService emailSenderService;
	
	@Autowired
	MUserRepository mUserRepository;
	
	Random rand = new Random();
	
	private class UpdateToken implements Runnable {
		TTokenRepository tTokenRepository;
		
		private TToken t;
		UpdateToken(TToken token, TTokenRepository tts){
			t = token;
			tTokenRepository = tts;
		}
		
        public void run()
        {
        	t.setIsExpired(true);
        	t.setModifiedBy(t.getUserId());
        	t.setModifiedOn(new Date());
        	TToken tkn = this.tTokenRepository.save(t);
            System.out.println("Token "+t.getToken()+" Unactived");
        }
    }
	
	@PostMapping("create/{use}")
	public ResponseEntity<Object> createToken(@RequestBody MUser users,@PathVariable("use") int use){
		
		//Jikalau mau membuat akses OTP, sesuaikan use dengan apa yang dilakukan. Kalau belum ada,
		//tambahkan elseifnya
		// 1 = Sign Up
		String usefor = "";
		if(use==1) {
			usefor = "SignUp";
		}else if(use==2) {
			usefor = "forgotPassword";
		}else if(use==3) {
			usefor = "changeEmail";
		}else {
			usefor = "Undefined";
		}
		
		try {
			List<TToken> activeTokens = this.tTokenRepository.findActiveTokens(users.getId());
			for(int i=0;i<activeTokens.size();i++) {
				if(activeTokens.get(i).getUsedFor().equals(usefor)) {
					activeTokens.get(i).setIsExpired(true);
					activeTokens.get(i).setModifiedBy(activeTokens.get(i).getUserId());
					activeTokens.get(i).setModifiedOn(new Date());
					this.tTokenRepository.save(activeTokens.get(i));
				}
			}
		}catch(Exception e) {
			
		}
		
		TToken token = new TToken();
		token.setCreatedBy(users.getId());
		token.setCreatedOn(new Date());
		token.setEmail(users.getEmail());
		token.setIsDelete(false);
		token.setIsExpired(false);
		token.setUsedFor(usefor);
		token.setUserId(users.getId());
		token.setExpiredOn(new Date(token.getCreatedOn().getTime() + (60*10*1000)));
		
		String myToken = "";
		for(int i=0;i<6;i++) {
			myToken += "" + rand.nextInt(10);
		}
		
		token.setToken(myToken);
		
		UpdateToken ut = new UpdateToken(token, this.tTokenRepository);
		
		taskScheduler.schedule(ut,token.getExpiredOn().toInstant());
		
		TToken saveToken = this.tTokenRepository.save(token);
		if(saveToken.equals(token)) {
			if(use == 1) {
				emailSenderService.sendEmail(token.getEmail(), "OTP Token", "Thank you for joining Solid Ayoyo Medical! Your token is:"+token.getToken());
			} else if(use == 2) {
				emailSenderService.sendEmail(token.getEmail(), "OTP Token", "We received a request to reset password! Your token is:"+token.getToken());
			} else if(use == 3) {
				emailSenderService.sendEmail(token.getEmail(), "OTP Token", "We received a request to change email! Your token is:"+token.getToken());
			} else {
				emailSenderService.sendEmail(token.getEmail(), "OTP Token", "Your token is:"+token.getToken());
			}
			
			return new ResponseEntity<>("Token Saved", HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>("Token Save Failed", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("check/{id}/{token}")
	public ResponseEntity<Object> checkToken(@PathVariable("id")Long id, @PathVariable("token") String token){
		try {
			List<TToken> tokens = this.tTokenRepository.findTokenByToken(token);
			if(tokens.isEmpty()) {
				return new ResponseEntity<>("Tidak ada token!", HttpStatus.OK);
			}else {
				for(int i=0;i<tokens.size();i++) {
					if(tokens.get(i).getUserId()==id) {
						if(tokens.get(i).getIsExpired()) {
							return new ResponseEntity<>("Token telah Kadaluarsa!", HttpStatus.OK);
						}else {
							tokens.get(i).setIsExpired(true);
							tokens.get(i).setModifiedBy(tokens.get(i).getUserId());
							tokens.get(i).setModifiedOn(new Date());
							this.tTokenRepository.save(tokens.get(i));
							return new ResponseEntity<>("Token Ditemukan!", HttpStatus.OK);
						}
					}
				}
				return new ResponseEntity<>("Tidak ada token!", HttpStatus.OK);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(" Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
