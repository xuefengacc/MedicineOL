package com.xuefeng.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xuefeng.model.Mail;
import com.xuefeng.model.MailFile;

@Repository
public interface FileRepository extends CrudRepository<MailFile, Long> {
	
	@Query(value="SELECT f FROM MailFile f WHERE f.mail = :mail")
	public List<MailFile> findByMail(@Param("mail") Mail mail);
	
}
