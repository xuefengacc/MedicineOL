package com.xuefeng.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="role")
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long id;
	private String name;

	@ManyToMany(mappedBy="roles")
	private List<Store> stores;

	public Role() {}
	
	public Role(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Store> getStore() {
		return stores;
	}

	public void setStore(List<Store> stores) {
		this.stores = stores;
	}
	
	
}
