package com.xuefeng.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.xuefeng.model.MedicineKey;


public interface MedKeyRepository extends CrudRepository<MedicineKey, Long> {

	@Query(value="SELECT k FROM MedicineKey k WHERE lower(k.medKey) LIKE %:name%")
	List<MedicineKey> findByNameLike(@Param("name") String search);
	
	@Query(value="FROM MedicineKey k WHERE k.medKey =:name")
	MedicineKey findByName(@Param("name") String name);
	
}
