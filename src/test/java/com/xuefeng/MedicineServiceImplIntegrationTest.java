package com.xuefeng;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.xuefeng.dao.ItemRepository;
import com.xuefeng.dao.StoreRepository;
import com.xuefeng.model.Item;
import com.xuefeng.model.Store;
import com.xuefeng.services.MedicineService;
import com.xuefeng.services.MedicineServiceImpl;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class MedicineServiceImplIntegrationTest {

	/**
	 * To check the Service class, we need to have an instance 
	 * of Service class created and available as a @Bean so that 
	 * we can @Autowire it in our test class. This configuration 
	 * is achieved by using the @TestConfiguration annotation
	 * */
	@TestConfiguration
	static class MedicineServiceImplContextConfiguration {
		
		@Bean
		public MedicineService medicineService() {
			return new MedicineServiceImpl();
		}
		
	}
	
	@Autowired
	private MedicineService service;
	/**
	 * @MockBean, It creates a Mock for the ItemRepository 
	 * which can be used to bypass the call to the actual 
	 * ItemRepository, but we're not using it here
	 * */
	@MockBean
	private ItemRepository itemrepo;
	
	@MockBean
	private StoreRepository storerepo;
	
	Store store = new Store();
	Item item = new Item();
	List<Item> itemList = new ArrayList<>();
	
	@Before
	public void setup() {
		
		initTestStore(store);
		initTestItem(item, store);
		
		itemList.add(item);
		
		//Mockito.when(storerepo.findById(store.getStoreId()).get())
		//    .thenReturn(store);
		
		//Mockito.when(itemrepo.findByName(item.getItemName()))
		//    .thenReturn(itemList);
	}
	
	@Test
	public void whenValidId_thenStoreShouldBeFound() {
		
		long id = 100;
		
		Store found = service.getStoreById(id);
		
		assertThat(found.getStoreId())
		    .isEqualTo(id);
	}
	
	
	
	//Initial testing Item object
		private void initTestItem(Item item, Store store) {
			
			//item.setItemId();
			item.setItemName("Medicine Sample");
			item.setPrice(0);
			item.setProducer("MOL");
			item.setCatalog(null);
			item.setStorage(100);
			item.setType("MEDICINE");
			item.setStore(null);
			item.setBloodType(null);
			item.setBloodVolum(0);
			item.setImagePath(null);
			item.setKeyword(null);
			
		}
		//Initial testing Store object
		private void initTestStore(Store store) {
			
			//store.setStoreId();
			store.setStoreName("Sample Store");
			store.setEmail("sample@test.com");
			store.setType("MEDICAL STORE");
			store.setWebSite(null);
			store.setAddress(null);
			store.setPhone(100);
			store.setPassword("000");
			store.setRoles(null);
			store.setItems(null);
			store.setDist(null);
			
		}
}
