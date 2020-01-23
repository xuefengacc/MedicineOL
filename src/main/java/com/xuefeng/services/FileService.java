package com.xuefeng.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.xuefeng.dao.FileRepository;
import com.xuefeng.model.Mail;
import com.xuefeng.model.MailFile;

@Service
public class FileService{

	@Value("${app.upload.dir}")
	public String uploadDir;
	
	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	@Autowired
	private FileRepository repo;
	
	//---------------------------For Image, no entity-------------------------------------------
	
	public String uploadFile(MultipartFile file, long itemId) throws Exception {
		logger.info("Saving File");
		Path copyLocation = Paths.get("G:"+File.separator+"SpringProject"+File.separator+"images"
		    +File.separator+itemId+StringUtils.cleanPath(file.getOriginalFilename()));
		
		Files.copy(file.getInputStream(),copyLocation, StandardCopyOption.REPLACE_EXISTING);
		
		return copyLocation.toString();
		
	}
	
	public Resource downLoadFile(Path fileLocation) throws Exception {
		logger.info("Path {}",fileLocation);
		Resource resource = new UrlResource(fileLocation.toUri());
		if(resource.exists()) {
			return resource;
		}
		return null;
	}
	
	public void deleteFile(String path) throws Exception{
		logger.info("Deleting file");
		if(!path.isEmpty()&&path!=null) {
			Path deletePath = Paths.get(path);
			Files.delete(deletePath);
			logger.info("File deleted");
		}
	}
	
	//-------------------------For email file, entity support-------------------------------
	
	public MailFile saveMailFile(MailFile file) {
		return repo.save(file);
	}
	
	public void deleteMailFile(long fileId) {
		repo.deleteById(fileId);
	}
	
	public List<MailFile> getFileByMail(Mail mail){
		return repo.findByMail(mail);
	}
}
