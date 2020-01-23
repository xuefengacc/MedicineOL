package com.xuefeng.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xuefeng.dao.ItemRepository;
import com.xuefeng.dao.MedKeyRepository;
import com.xuefeng.dao.StoreRepository;
import com.xuefeng.model.Item;
import com.xuefeng.model.MedicineKey;
import com.xuefeng.model.Store;

@Service
public class MedicineServiceImpl implements MedicineService{

	@Autowired
	protected StoreRepository storeRepo;
	@Autowired
	protected ItemRepository itemRepo;
	@Autowired
	protected MedKeyRepository keyRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(MedicineServiceImpl.class);
	//private final StoreRepository storeRepo;
	//private final ItemRepository itemRepo;
	
	//public MedicineServiceImpl(StoreRepository storeRepo, ItemRepository itemRepo){
		//this.storeRepo = storeRepo;
		//this.itemRepo = itemRepo;
	//}
	
	
	//---------------------------------------------------------
	//------------------------Store Operation------------------
	//---------------------------------------------------------
	@Override
	public Store getStoreById(long id) {
		return storeRepo.findById(id).get();
	}

	@Override
	public List<Store> getMedicalStore() {
		return storeRepo.findByType("MEDICALSTORE");
	}

	@Override
	public List<Store> getBloodStore() {
		return storeRepo.findByType("BLOODSTORE");
	}

	@Override
	public List<Store> getAllStore() {
		return (List<Store>) storeRepo.findAll();
	}

	@Override
	public List<Store> searchStoreByName(String name) {
		return storeRepo.findByNameLike(name);
	}

	@Override
	public Store saveStore(Store store) {
		return storeRepo.save(store);
	}

	
	@Override
	public void deleteStoreById(long id) {
		storeRepo.deleteById(id);		
	}
	
	
	//---------------------------------------------------------------
	//----------------------------Item Operaion----------------------
	//---------------------------------------------------------------
	
	@Override
	public Item getItemById(long id) {
		return itemRepo.findById(id).get();
	}

	@Override
	public List<Item> getAllItems() {
		return (List<Item>) itemRepo.findAll();
	}

	@Override
	public List<Item> getAllBlood() {
		return itemRepo.findByType("BLOOD");
	}

	@Override
	public List<Item> getAllMedicine() {
		return itemRepo.findByType("MEDICINE");
	}

	@Override
	public List<Item> getItemByStore(Store store) {
		return itemRepo.findByStore(store);
	}

	@Override
	public List<Item> getMedicineByCatalog(String catalog) {
		return itemRepo.findByCatalog(catalog);
	}

	@Override
	public List<Item> searchItemByName(String name) {
		return itemRepo.findByNameLike(name);
	}

	@Override
	public List<Item> searchMedicineByCatalog(String catalog) {
		return itemRepo.findByCatalogLike(catalog);
	}

	@Override
	public Item saveItem(Item item) {
		item.setStoreName();
		item.setStoreEmail();
		if(item.getType().equals("MEDICINE")) {
			logger.info("Set Keyword");
			MedicineKey key = keyRepo.findByName(item.getItemName());
			if(key!=null) {
				item.setKeyword(key);
			} else {
				MedicineKey newkey = new MedicineKey(item.getItemName());
				item.setKeyword(keyRepo.save(newkey));
			}			
		}
		return itemRepo.save(item);
	}

	@Override
	public void deleteItem(long id) {
		itemRepo.deleteById(id);
	}

	@Override
	public Store getStoreByEmail(String email) {
		return storeRepo.findByEmail(email);
	}

	@Override
	public List<Item> searchMedicineByName(String name) {
		return itemRepo.findByTypeAndNameLike(name);
	}

	@Override
	public List<Item> getBloodByType(String bloodtype) {
		return itemRepo.findByTypeAndBloodType(bloodtype);
	}

	@Override
	public List<Store> SearchMedicalStoreByName(String name) {
		return storeRepo.findByTypeAndNameLike("MEDICALSTORE", name);
	}

	@Override
	public List<Store> SearchBloodStoreByName(String name) {
		return storeRepo.findByTypeAndNameLike("BLOODSTORE", name);
	}

	@Override
	public Store getStoreByName(String name) {
		return storeRepo.findByName(name);
	}

	@Override
	public List<MedicineKey> searchMedicineKey(String name) {
		List<MedicineKey> keys = keyRepo.findByNameLike(name);
		//delete keyword that refers no entity
		for(int i = 0;i < keys.size();i++) {
			if(keys.get(i).getItems().isEmpty()) {
				long id = keys.get(i).getKeyId();
				keys.remove(i);
				keyRepo.deleteById(id);
			}
		}
		return keys;
	}
	
}
