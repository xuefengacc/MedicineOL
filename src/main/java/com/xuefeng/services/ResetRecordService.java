package com.xuefeng.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuefeng.dao.VerifyCodeRepository;
import com.xuefeng.model.VerifyCode;

@Service
public class ResetRecordService {

	@Autowired
	private VerifyCodeRepository repo;
	
	public void saveVerifCode(VerifyCode re) {
		repo.save(re);
	}
	
	public List<VerifyCode> getCodeByEmail(String email) {
		return repo.findByEmail(email);
	}
	
	public void deleteCode(long id) {
		repo.deleteById(id);
	}
}
