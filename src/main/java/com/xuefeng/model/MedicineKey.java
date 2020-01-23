package com.xuefeng.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="medicinekey_list")
public class MedicineKey {
    
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long keyId;
	@NotNull
	@Column(unique = true)
	private String medKey;
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="itemKeyword", cascade = CascadeType.ALL)
	private List<Item> items;
	
	public MedicineKey() {}
	
	public MedicineKey(@NotNull String medKey) {
		this.medKey = medKey;
	}

	public long getKeyId() {
		return keyId;
	}


	public String getMedKey() {
		return medKey;
	}

	public void setMedKey(String medKey) {
		this.medKey = medKey;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}
