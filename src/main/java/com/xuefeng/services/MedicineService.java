package com.xuefeng.services;

import java.util.List;

import com.xuefeng.model.Item;
import com.xuefeng.model.MedicineKey;
import com.xuefeng.model.Store;

public interface MedicineService {

	//---------------------------------------------
		//---------------Store Opeartion---------------
		//---------------------------------------------
		Store getStoreById(long id);
		List<Store> getMedicalStore();
		List<Store> getBloodStore();
		Store getStoreByName(String name);
		List<Store> getAllStore();
		List<Store> searchStoreByName(String name);
		Store getStoreByEmail(String email);
		List<Store> SearchMedicalStoreByName(String name);
		List<Store> SearchBloodStoreByName(String name);
		
		Store saveStore(Store store);
		void deleteStoreById(long id);
		
		//----------------------------------------------
		//----------------Item Operation----------------
		//----------------------------------------------
		
		Item getItemById(long id);
		List<Item> getAllItems();
		List<Item> getAllBlood();
		List<Item> getAllMedicine();
		List<Item> getItemByStore(Store store);
		List<Item> getMedicineByCatalog(String catalog);
		List<Item> searchItemByName(String name);
		List<Item> searchMedicineByCatalog(String catalog);
		
		List<Item> searchMedicineByName(String name);
		List<Item> getBloodByType(String bloodtype);
		
		Item saveItem(Item item);
		void deleteItem(long id);
		
		//------------------------------------------------
		//------------------Key Word----------------------
		//------------------------------------------------
		List<MedicineKey> searchMedicineKey(String name);
	
}
