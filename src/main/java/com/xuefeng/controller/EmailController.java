package com.xuefeng.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuefeng.model.Mail;
import com.xuefeng.model.MailFile;
import com.xuefeng.services.EmailService;
import com.xuefeng.services.FileService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class EmailController {

	@Autowired
	EmailService service;
	@Autowired
	FileService fileService;
	
	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
	
	@RequestMapping(value="/mail/saved", method=RequestMethod.POST)
	public ResponseEntity<Mail> saveEmail(@RequestBody Mail mail) throws MessagingException{
		logger.info("Saving email...");
		logger.info("{}", mail.getMailSubject());
		Mail savedMail = service.saveEmail(mail);
		
		return new ResponseEntity(savedMail, HttpStatus.OK);
	}
	
	@RequestMapping(value="/mail/send", method=RequestMethod.POST)
	public ResponseEntity<?> sendEmail(@RequestBody Mail mail) throws IOException, Exception{
		logger.info("Sending email...");
		Mail mailToSend = service.getMailById(mail.getMailId());
		service.sendEmail(mailToSend);
		return new ResponseEntity(HttpStatus.OK);
	} 
	
	@RequestMapping(value="/mail/list/{email}", method=RequestMethod.GET)
	public ResponseEntity<?> getAllEmailOfStore(@PathVariable("email") String email){
		logger.info("Get the list all all its email history");
		List<Mail> list = service.getMailsByStore(email);
		return new ResponseEntity(list, HttpStatus.OK);
	}
	
	@RequestMapping(value="/mail/delete/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<?> deleteEmailById(@PathVariable("id") long id) throws Exception{
		logger.info("Deletting email");
		Mail mail = service.getMailById(id);
		List<MailFile> files = mail.getMailFiles();
		if(!files.isEmpty()) {
			for(MailFile file:files) {
				fileService.deleteFile(file.getFilePath());
			}
		}
		service.deleteMail(id);
		return new ResponseEntity(HttpStatus.OK);
	}
	
}
