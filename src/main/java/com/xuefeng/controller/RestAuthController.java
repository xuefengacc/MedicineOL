package com.xuefeng.controller;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuefeng.model.Mail;
import com.xuefeng.model.Store;
import com.xuefeng.model.VerifyCode;
import com.xuefeng.services.EmailService;
import com.xuefeng.services.MedicineService;
import com.xuefeng.services.ResetRecordService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class RestAuthController {

	@Autowired
	private EmailService service;
	@Autowired
	private ResetRecordService record;
	@Autowired
	private MedicineService med;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	private static final Logger logger = LoggerFactory.getLogger(RestAuthController.class);
	
	@RequestMapping(value="/login/reset", method = RequestMethod.POST)
	public ResponseEntity<?> generateVerificationCode(@RequestBody String email){
		
		//TODO better algorithm
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		
		while(salt.length()<9) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String salter = salt.toString();
		
		Mail mail = new Mail();
		mail.setMailFrom("server@medical.com");
		mail.setMailTo(email);
		mail.setMailSubject("Reset Password");
		mail.setMailText("Your verification Code is "
				+salter+".");
		try {
			service.sendEmail(mail);
			VerifyCode reseting = new VerifyCode(email,salter);
			record.saveVerifCode(reseting);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value="/login/check/{email}", method=RequestMethod.POST)
	public ResponseEntity<Store> verifCode(@PathVariable("email") String email, @RequestBody String code){
		List<VerifyCode> list = record.getCodeByEmail(email);
		logger.info("Check Code {}", code);
		for(VerifyCode ver:list) {
			if(code.equals(ver.getVerifyCode())) {
				return new ResponseEntity<Store>(med.getStoreByEmail(email), HttpStatus.OK);
			} 
		}
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);	
	} 
	
	@RequestMapping(value="/login/send", method=RequestMethod.POST)
    public ResponseEntity<?> autoPassword(@RequestBody String email) throws IOException, Exception{
        Store store = med.getStoreByEmail(email);
        //Delete all code
        List<VerifyCode> codes = record.getCodeByEmail(email);
        for(VerifyCode code: codes) {
        	record.deleteCode(code.getVerifyCodeId()); 
        }
        //Generate new password
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while(salt.length()<9) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String salter = salt.toString();
		store.setPassword(bcryptEncoder.encode(salter));
		med.saveStore(store);
		//Send new password to user
		Mail mail = new Mail();
		mail.setMailFrom("server@admin.com");
		mail.setMailTo(email);
		mail.setMailSubject("Temporary Password");
		mail.setMailText("Your temporary password is "+salter+"."+"Please "
				+ "change your password once login.");
		service.sendEmail(mail);
		return new ResponseEntity(HttpStatus.OK);
    }
    
}
