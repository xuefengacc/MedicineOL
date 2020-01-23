package com.xuefeng.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.apache.commons.io.IOUtils;
import com.xuefeng.dao.MailRepository;
import com.xuefeng.model.Mail;
import com.xuefeng.model.MailFile;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private MailRepository repo;
	@Autowired
	private FileService service;
	
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
	private static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
	
	public void sendEmail(Mail mail) throws IOException, Exception {
		MimeMessage msg = javaMailSender.createMimeMessage();
		
		// true = multipart message
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		
        //Attache all the files of the email if any
		if(mail.getMailFiles()!=null) {
			for(MailFile file: mail.getMailFiles()) {
				if(file.getFilePath()!=null) {
					String name = file.getFileName();
					Path path = Paths.get(file.getFilePath());
					InputStream resource = service.downLoadFile(path).getInputStream();
					helper.addAttachment(name, new ByteArrayResource(IOUtils.toByteArray(resource)));
					resource.close();
				}
			}
		}
		
		helper.setFrom(mail.getMailFrom());
		helper.setTo(mail.getMailTo());
		helper.setSubject(mail.getMailSubject());
		helper.setText(mail.getMailText());
	
		javaMailSender.send(msg);

	}
	
	public Mail saveEmail(Mail mail) {
		return repo.save(mail);
	}
	
	public List<Mail> getMailsByStore(String storeEmail){
		return repo.findByFrom(storeEmail);
	}
	
	public void deleteMail(long id) {
		repo.deleteById(id);
	}
	
	public Mail getMailById(long id) {
		return repo.findById(id).get();
	}
}
