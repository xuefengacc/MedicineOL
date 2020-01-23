package com.xuefeng.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="verify_code")
public class VerifyCode {

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long verifyCodeId;
	private String verifyEmail;
	private String verifyCode;
	
	public VerifyCode() {}

	public VerifyCode(String verifyEmail, String verifyCode) {
		this.verifyEmail = verifyEmail;
		this.verifyCode = verifyCode;
	}

	public long getVerifyCodeId() {
		return verifyCodeId;
	}

	public void setVerifyCodeId(long verifyCodeId) {
		this.verifyCodeId = verifyCodeId;
	}

	public String getVerifyEmail() {
		return verifyEmail;
	}

	public void setVerifyEmail(String verifyEmail) {
		this.verifyEmail = verifyEmail;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	
}
