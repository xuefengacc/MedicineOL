package com.xuefeng.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xuefeng.model.Item;
import com.xuefeng.model.Store;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

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
	@Query(value="FROM Item i WHERE i.itemName =:name")
	List<Item> findByName(@Param("name") String name);
	
	@Query(value="SELECT i FROM Item i WHERE i.producer = :prod")
	List<Item> findByProducer(@Param("prod") String producer);

	@Query(value="SELECT i FROM Item i WHERE lower(i.itemName) LIKE %:name%")
	List<Item> findByNameLike(@Param("name") String search);
	
	@Query(value="SELECT i FROM Item i WHERE lower(i.catalog) LIKE %:catalog% AND i.type ='MEDICINE'")
	List<Item> findByCatalogLike(@Param("catalog") String search);
	
	@Query(value="SELECT i FROM Item i WHERE i.catalog = :log AND i.type='MEDICINE'")
	List<Item> findByCatalog(@Param("log") String search);
	
	@Query(value="SELECT i FROM Item i WHERE i.type = :type")
	List<Item> findByType(@Param("type") String type);
	
	@Query(value="SELECT i FROM Item i WHERE i.store = :store")
	List<Item> findByStore(@Param("store") Store store);
	
	@Query(value="SELECT i FROM Item i WHERE i.type='MEDICINE' AND lower(i.itemName) LIKE %:name%")
	List<Item> findByTypeAndNameLike(@Param("name") String name);
	
	@Query(value="SELECT i from Item i WHERE i.type='Blood' AND i.bloodType = :bloodtype")
	List<Item> findByTypeAndBloodType(@Param("bloodtype") String bloodtype);
	
}
	
