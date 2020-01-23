package com.xuefeng.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.xuefeng.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	List<Role> findByName(String name);
	
}
