package com.xuefeng.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xuefeng.model.Mail;

@Repository
public interface MailRepository extends CrudRepository<Mail, Long>{

	@Query(value="SELECT m FROM Mail m WHERE m.mailFrom = :from")
	public List<Mail> findByFrom(@Param("from") String from);
	
}
