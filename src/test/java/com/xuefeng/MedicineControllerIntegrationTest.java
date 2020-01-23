package com.xuefeng;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.xuefeng.controller.MedicalController;
import com.xuefeng.model.Item;
import com.xuefeng.model.Store;
import com.xuefeng.services.MedicineService;

@RunWith(SpringRunner.class)
@WebMvcTest(MedicalController.class)
public class MedicineControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private MedicineService service;
	
	@Test
	public void givenStores_whenGetStores_thenReturnJsonArray() throws Exception {
		
		Store store = new Store();
		
		List<Store> stores = new ArrayList<>();
		stores.add(initTestStore(store));
		
		//given(service.getAllStore()).willReturn(stores);
		
		mvc.perform(get("/store")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());
			      //.andExpect(jsonPath("$", hasSize(1)))
			      //.andExpect();
	}
	
	
	
	//Initial testing Item object
			private Item initTestItem(Item item, Store store) {
				
				item.setItemId(1);
				item.setItemName("Medicine Sample");
				item.setPrice(0);
				item.setProducer("MOL");
				item.setCatalog(null);
				item.setStorage(100);
				item.setType("MEDICINE");
				item.setStore(null);
				item.setBloodType(null);
				item.setBloodVolum(0);
				
				return item;
			}
			//Initial testing Store object
			private Store initTestStore(Store store) {
				store.setStoreId(1);
				store.setStoreName("Sample Store");
				store.setEmail("sample@test.com");
				store.setType("MEDICAL STORE");
				store.setWebSite(null);
				store.setAddress(null);
				store.setPhone(100);
				store.setPassword("000");
				store.setRoles(null);
				store.setItems(null);
				
				return store;
			}
}
