package com.xuefeng.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="mail_list")
public class Mail {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long mailId;
	private String mailTo;
	private String mailSubject;
	private String mailText;
	private String mailFrom;
	private String sendDateTime;
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "mail",cascade = CascadeType.ALL)
	private List<MailFile> mailFiles;
	
	public long getMailId() {
		return mailId;
	}
	public void setMailId(long mailId) {
		this.mailId = mailId;
	}
	public String getMailTo() {
		return mailTo;
	}
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	public String getMailSubject() {
		return mailSubject;
	}
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	public String getMailText() {
		return mailText;
	}
	public void setMailText(String mailText) {
		this.mailText = mailText;
	}
	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	public List<MailFile> getMailFiles() {
		return mailFiles;
	}
	public void setMailFiles(List<MailFile> mailFiles) {
		this.mailFiles = mailFiles;
	}
	public String getSendDateTime() {
		return sendDateTime;
	}
	public void setSendDateTime(String sendDateTime) {
		this.sendDateTime = sendDateTime;
	}
	
	
}
