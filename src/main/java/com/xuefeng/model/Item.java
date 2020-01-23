package com.xuefeng.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="item_list")
public class Item {

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long itemId;
	private String itemName;
	private double price;
	private String producer;
	private String catalog;
	private int storage;
	private int bloodVolum;
	private String bloodType;
	private String type;//MEDICINE  BLOOD
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="store_id", nullable=false)
	private Store store;
    private String imagePath;
	private String storeName;
	private String storeEmail;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="keyword")
	private MedicineKey itemKeyword;
	
	public Item() { }
	
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public int getStorage() {
		return storage;
	}
	public void setStorage(int storage) {
		this.storage = storage;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public String getBloodType() {
		return bloodType;
	}
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	public int getBloodVolum() {
		return bloodVolum;
	}
	public void setBloodVolum(int bloodVolum) {
		this.bloodVolum = bloodVolum;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName() {
		if(this.store != null) {
			this.storeName = this.store.getStoreName();
		}
	}
	public String getStoreEmail() {
		return storeEmail;
	}
	public void setStoreEmail() {
		if(this.store != null) {
			this.storeEmail = this.store.getEmail();
		}
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public MedicineKey getKeyword() {
		return itemKeyword;
	}

	public void setKeyword(MedicineKey keyword) {
		this.itemKeyword = keyword;
	}
	
}
