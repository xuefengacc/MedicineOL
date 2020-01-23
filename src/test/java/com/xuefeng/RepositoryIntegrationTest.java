package com.xuefeng;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.xuefeng.dao.ItemRepository;
import com.xuefeng.dao.StoreRepository;
import com.xuefeng.model.Item;
import com.xuefeng.model.Store;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class RepositoryIntegrationTest {

	/**
	 * Test customize method in repository
	 * Item: -findByName, findByProducer, -findNameLike, findByCatalogLike, findByCatalog
	 *       findByType, findByStore
	 * Store: -findByEmail, -findByNameLike, findByAddressLike, findByType
	 * */
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private ItemRepository itemrepo;
	
	@Autowired
	private StoreRepository storepo;
	
	Store store = new Store();
	Item item = new Item();
	
	@Before
	public void startup() {
		initTestStore(store);
		initTestItem(item, store);
		
		entityManager.persist(store);
		entityManager.persist(item);
		entityManager.flush();
	}
	
	@Test
	public void whenFindByName_thenReturnItemList() {
		
		//when
		List<Item> found = itemrepo.findByName(item.getItemName());
		
		//then
		assertThat(found.get(0).getItemName())
		    .isEqualTo(item.getItemName());
	}
	
	@Test
	public void whenFindByNameLike_thenReturnItemList() {
	    
		//when
		List<Item> found = itemrepo.findByName(item.getItemName());
		
		//then
		assertThat(found.get(0).getItemName())
		    .isEqualTo(item.getItemName());
	}

	@Test
	public void whenFindByEmail_thenReturnStore() {
		//when
		Store found = storepo.findByEmail(store.getEmail());
		
		//then
		assertThat(found.getEmail())
		    .isEqualTo(store.getEmail());
	}
	
	@Test
	public void whenFindAll_thenReturnStore() {
		//when
		List<Store> found = (List<Store>) storepo.findAll();
		
		//then
		assertThat(found.get(0).getEmail())
		    .isEqualTo(store.getEmail());
	}
	
	
	//Initial testing Item object
	private void initTestItem(Item item, Store store) {
		
		//item.setItemId(100);
		item.setItemName("Medicine Sample");
		item.setPrice(0);
		item.setProducer("MOL");
		item.setCatalog(null);
		item.setStorage(100);
		item.setType("MEDICINE");
		item.setStore(store);
		item.setBloodType(null);
		item.setBloodVolum(0);
		item.setImagePath(null);
		item.setStoreEmail();
		item.setStoreName();
		item.setKeyword(null);
		
	
	}
	//Initial testing Store object
	private void initTestStore(Store store) {
		//store.setStoreId(100);
		store.setStoreName("Sample Store");
		store.setEmail("sample@test.com");
		store.setType("MEDICALSTORE");
		store.setWebSite(null);
		store.setAddress(null);
		store.setPhone(100);
		store.setPassword("000");
		store.setRoles(null);
		store.setItems(null);
		store.setDist(null);
	}
	
}
