package com.xuefeng.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xuefeng.model.Store;

@Repository
public interface StoreRepository extends CrudRepository<Store, Long>{

	/*
	 * count() return long
	 * delete(T entity) 
	 * deleteAll()
	 * deleteAll(Iterable<? extends T> entites)
	 * deleteById(ID id)
	 * existsById(ID id) return boolean
	 * findAll() Iterable<T>
	 * findAllById(Iterable<ID> ids) return Iterable<T>
	 * findById(ID id) Optional<T>
	 * save(S entity) return <S extends T> S
	 * saveAll(Iterable<S> entities) return Iterable S
	 * 
	 * */
	
	boolean existsByEmail(String email);
	
	@Query(value="SELECT s FROM Store s WHERE s.email = :email")
	Store findByEmail(@Param("email") String email);
	
	@Query(value="SELECT s FROM Store s WHERE lower(s.storeName) LIKE %?1%")
	List<Store> findByNameLike(String name);
	
	
	@Query(value="SELECT s FROM Store s WHERE lower(s.address) LIKE %?1%") 
	List<Store> findByAddressLike(String address);
	
	@Query(value="SELECT s FROM Store s WHERE s.type = :type")
	List<Store> findByType(@Param("type") String type);
	
	@Query(value="SELECT s FROM Store s WHERE lower(s.storeName) =:name")
	Store findByName(@Param("name") String name);
	
	@Query(value="SELECT s FROM Store s WHERE s.type=?1 AND lower(s.storeName) LIKE %?2%")
	List<Store> findByTypeAndNameLike(String type, String name);
	
}
