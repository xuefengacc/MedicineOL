package com.xuefeng.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xuefeng.model.VerifyCode;

@Repository
public interface VerifyCodeRepository extends CrudRepository<VerifyCode, Long> {

	@Query(value="SELECT v FROM VerifyCode v WHERE v.verifyEmail = :email")
	public List<VerifyCode> findByEmail(@Param("email") String email);
	
}
