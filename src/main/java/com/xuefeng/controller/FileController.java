package com.xuefeng.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;

import com.xuefeng.model.FileDTO;
import com.xuefeng.model.Item;
import com.xuefeng.model.Mail;
import com.xuefeng.model.MailFile;
import com.xuefeng.model.Store;
import com.xuefeng.services.EmailService;
import com.xuefeng.services.FileService;
import com.xuefeng.services.MedicineService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class FileController {
    
	@Autowired
	private MedicineService itemService;
	@Autowired
	private FileService fileService;
	@Autowired
	private EmailService mailService;
	
	private final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	//-------------------------------------------------------------------------------------------
	
	/**
	 * Save the image to its item
	 * */
	@RequestMapping(value="/manager/image/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> saveImage(@PathVariable("id") long id, @RequestParam("item image") MultipartFile file) throws Exception{
		logger.info("Uploading Image");

	    Item item = itemService.getItemById(id);
	    String path = fileService.uploadFile(file, item.getItemId());
	    item.setImagePath(path);
        itemService.saveItem(item);
	    return new ResponseEntity(HttpStatus.OK);
	}
	
	/**
	 * Get the image of certain item
	 * @throws Exception 
	 * */
	@RequestMapping(value="/store/image/{id}", method = RequestMethod.GET)
	public ResponseEntity<FileDTO> getImage(@PathVariable("id") long id) throws Exception{
		logger.info("Downloading Image");
		Item item = itemService.getItemById(id);
		String path = item.getImagePath();
		if(path!=null) {
			Path file = Paths.get(path);
			InputStream resource = fileService.downLoadFile(file).getInputStream();
			byte[] image = IOUtils.toByteArray(resource);
            FileDTO dto = new FileDTO(image);
            resource.close();
			return new ResponseEntity<FileDTO>(dto,HttpStatus.OK);
		}
		
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Save the background image of each store
	 * */
	@RequestMapping(value="/manager/background/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> saveBackground(@PathVariable("id") long id, @RequestParam("item image") MultipartFile file) throws Exception{
		logger.info("Uploading Background Image");

	    Store store = itemService.getStoreById(id);
	    String path = fileService.uploadFile(file, store.getStoreId());
	    store.setImagePath(path);
        itemService.saveStore(store);
	    return new ResponseEntity(HttpStatus.OK);
	}
	
	/**
	 * Get the Background image of certain item
	 * @throws Exception 
	 * */
	@RequestMapping(value="/manager/background/{id}", method = RequestMethod.GET)
	public ResponseEntity<FileDTO> getBackground(@PathVariable("id") long id) throws Exception{
		logger.info("Downloading Background");
		Store store = itemService.getStoreById(id);
		String path = store.getImagePath();
		if(path!=null) {
			Path file = Paths.get(path);			
			InputStream resource = fileService.downLoadFile(file).getInputStream();
			byte[] image = IOUtils.toByteArray(resource);
            FileDTO dto = new FileDTO(image);
            resource.close();
			return new ResponseEntity<FileDTO>(dto,HttpStatus.OK);
		}
		
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	//------------------------------For Email File------------------------------------------------
	
	/**
	 * Save the attachment file of email
	 * */
	@RequestMapping(value="/mail/mailfile/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Mail> saveMailFile(@PathVariable("id") long id, @RequestParam("item image") MultipartFile file) throws Exception{
		logger.info("Uploading email file");
		//Prepare the saved email and create new file entity
        Mail mail = mailService.getMailById(id);
        MailFile mailFile = new MailFile();
        //Get the name and path of uploaded file
        String filePath = fileService.uploadFile(file, id);
        String fileName = file.getOriginalFilename();
        //Update the file entity and save into database
        mailFile.setFileName(fileName);
        mailFile.setFilePath(filePath);
        mailFile.setMail(mail);
        fileService.saveMailFile(mailFile);
        //Update the original email and save for sending
        
	    return new ResponseEntity<Mail>(mailService.getMailById(id),HttpStatus.OK);
	}
	
	/**
	 * Get the file attached to an email(input mail id return file)
	 * @throws Exception 
	 * */
	@RequestMapping(value="/mail/file/{id}", method = RequestMethod.GET)
	public ResponseEntity<FileDTO> getMailFile(@PathVariable("id") long id) throws Exception{
		logger.info("Downloading Mail file");
		
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
}
