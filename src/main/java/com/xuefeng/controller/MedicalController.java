package com.xuefeng.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import org.apache.commons.lang3.StringUtils;
import com.xuefeng.config.JwtTokenUtil;
import com.xuefeng.model.Item;
import com.xuefeng.model.MedicineKey;
import com.xuefeng.model.Store;
import com.xuefeng.services.FileService;
import com.xuefeng.services.MedicineServiceImpl;

@RestController
@CrossOrigin(origins="http://localhost:4200")
//@RequestMapping(value="/manager/")
public class MedicalController {

	@Autowired
	private MedicineServiceImpl service;
	@Autowired
	private FileService fileservice;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(MedicalController.class);
	
	//------------------------------------------------------------------------
	//--------------General Operation, no authrozie needed--------------------
    //------------------------------------------------------------------------
	
	//-----------------------------Store Operation----------------------------
	/** 
	    * Get single Store instance by Id. 
	    * @param long id The id from request. 
	    * @return ResponseEntity<Store>. 
	    */
	@RequestMapping(value="/store/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getStoreById(@PathVariable("id") long id){
		logger.info("Fetching Store with id {}", id);
		Store store = service.getStoreById(id);
		if(store == null) {
			logger.error("Could not found store with id {}", id);
			return new ResponseEntity<Store>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Store>(store, HttpStatus.OK);
	}
    
	/** 
	    * Get all Store instances. 
	    * @param null. 
	    * @return ResponseEntity<List<Store>>. 
	    */
	@RequestMapping(value="/store", method = RequestMethod.GET)
	public ResponseEntity<?> getStores(){
		logger.info("Fetching all stores");
		List<Store> stores = service.getAllStore();
		if(stores.isEmpty()) {
			logger.error("Could not found any store");
			return new ResponseEntity<List<Store>>(HttpStatus.NO_CONTENT);
		}
		stores.removeIf(store -> store.getStoreName().equals("Admin"));
		return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);
	}
	
	/** 
	    * Search store by name. VERY IMPORTANT TODO
	    * @param name.  
	    * @return ResponseEntity<Store>. 
	    */
	@RequestMapping(value="/store/search/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<Store>> searchStoreByName(@PathVariable("name") String name){
		logger.info("Searching for Store Whose Name contains {}", name);
		List<Store> stores = service.searchStoreByName(name);
		if(stores.isEmpty()) {
			logger.error("Could not find any store related to {}", name);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);
	}
	
	/** 
	    * List all medical store. 
	    * @param none.  
	    * @return ResponseEntity<List<Store>>. 
	    */
	@RequestMapping(value="/store/medical", method = RequestMethod.GET)
	public ResponseEntity<List<Store>> getMedicalStore(){
		logger.info("List of all medical store");
		List<Store> stores = service.getMedicalStore();
		if(stores.isEmpty()) {
			logger.error("Could not find any medical store");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);
	}
	
	/** 
	    * List all blood store. 
	    * @param none.  
	    * @return ResponseEntity<List<Store>>. 
	    */
	@RequestMapping(value="/store/blood", method = RequestMethod.GET)
	public ResponseEntity<List<Store>> getBloodStore(){
		logger.info("List of all medical store");
		List<Store> stores = service.getBloodStore();
		if(stores.isEmpty()) {
			logger.error("Could not find any medical store");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);
	}
	
	/** 
	    * Search medical store by Name. 
	    * @param name 
	    * @return ResponseEntity<List<Store>>. 
	    */
	@RequestMapping(value="/store/search/medical/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<Store>> searchMedicalStoreByName(@PathVariable("name") String name){
		logger.info("Searching for Medical Store Whose Name contains {}", name);
        if(StringUtils.isBlank(name)) {return new ResponseEntity(HttpStatus.NO_CONTENT);}		
		List<Store> stores = service.SearchMedicalStoreByName(name);
		if(stores.isEmpty()) {
			logger.error("Could not find any Medical store related to {}", name);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);
	}
	/* fake search */
	@RequestMapping(value="/store/search/medical/",method = RequestMethod.GET)
	public ResponseEntity<?> fakeSearchMedicalStore(){
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	
	/** 
	    * Search blood store by name. 
	    * @param none.  
	    * @return ResponseEntity<List<Store>>. 
	    */
	@RequestMapping(value="/store/search/blood/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<Store>> searchBloodStoreByName(@PathVariable("name") String name){
		logger.info("Searching for Blood Store Whose Name contains {}", name);
		if(StringUtils.isBlank(name)) {return new ResponseEntity(HttpStatus.NO_CONTENT);}
		List<Store> stores = service.SearchBloodStoreByName(name);
		if(stores.isEmpty()) {
			logger.error("Could not find any Blood store related to {}", name);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);
	}
	/* fake controller */
	@RequestMapping(value="/store/search/blood/",method = RequestMethod.GET)
	public ResponseEntity<?> fakeBloodStoreSearch(){
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
    /**
     * Get store by email
     * */
	@RequestMapping(value="/store/itemstore/{email}", method=RequestMethod.GET)
	public ResponseEntity<Store> getStoreByEmail(@PathVariable("email") String email){
		logger.info("Find store with email {}", email);
		Store store = new Store();
		store = service.getStoreByEmail(email);
		if(store!=null) {
			return new ResponseEntity<Store>(store, HttpStatus.OK);
		} else {
			return new ResponseEntity<Store>(HttpStatus.NOT_FOUND);
		}
	}
	
	//--------------------------Items Operation------------------------------
	
	/** 
	    * Get single Item by Id. 
	    * @param long id The id from request. 
	    * @return ResponseEntity<Store>. 
	    */
	@RequestMapping(value="/item/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getItemById(@PathVariable("id") long id){
		logger.info("Fetching Item with id {}", id);
		Item item = service.getItemById(id);
		if(item == null) {
			logger.error("Could not found Item with id {}", id);
			return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}

	/** 
	    * Get all Item instances. 
	    * @param null. 
	    * @return ResponseEntity<List<Item>>. 
	    */
	@RequestMapping(value="/item", method = RequestMethod.GET)
	public ResponseEntity<?> getItems(){
		logger.info("Fetching all items");
		List<Item> items = service.getAllItems();
		if(items.isEmpty()) {
			logger.error("Could not found any store");
			return new ResponseEntity<List<Item>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}
	
	/** 
	    * Search medicine by name. 
	    * @param name.  
	    * @return ResponseEntity<Item>. 
	    */
	@RequestMapping(value="/item/search/medicine/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<Item>> searchMedicineByName(@PathVariable("name") String name){
		logger.info("Searching for Medicine Whose Name contains {}", name);
		if(StringUtils.isBlank(name)) {return new ResponseEntity(HttpStatus.NO_CONTENT);}
		List<Item> items = service.searchMedicineByName(name);
		if(items.isEmpty()) {
			logger.error("Could not find any medicine related to {}", name);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}
	/* fake controller*/
	@RequestMapping(value="/item/search/medicine/",method = RequestMethod.GET)
	public ResponseEntity<?> fakeSearch(){
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Get the keyword for autocompelete in front-end
	 * @param string name
	 * @return ResponseEntity
	 * */
	@RequestMapping(value="/item/key/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<MedicineKey>> searchMedicineKey(@PathVariable("name") String name){
		logger.info("Getting the key for medicine");
		if(StringUtils.isBlank(name)) {return new ResponseEntity(HttpStatus.NO_CONTENT);}
		List<MedicineKey> keys = service.searchMedicineKey(name);
		if(keys.size()>10) {
			return new ResponseEntity<List<MedicineKey>>(keys.subList(0, 9), HttpStatus.OK);
		}
		return new ResponseEntity<List<MedicineKey>>(keys, HttpStatus.OK);
	}
	/* fake search */
	@RequestMapping(value="/item/key/", method = RequestMethod.GET)
	public ResponseEntity<?> fakeKeySearch(){
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	/** 
	    * List all medicines. 
	    * @param none.  
	    * @return ResponseEntity<List<Item>>. 
	    */
	@RequestMapping(value="/item/medicine", method = RequestMethod.GET)
	public ResponseEntity<List<Item>> getMedicines(){
		logger.info("List of all medicines");
		List<Item> items = service.getAllMedicine();
		if(items.isEmpty()) {
			logger.error("Could not find any medicine");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}
	
	/** 
	    * List all bloods. 
	    * @param none.  
	    * @return ResponseEntity<List<Item>>. 
	    */
	@RequestMapping(value="/item/blood", method = RequestMethod.GET)
	public ResponseEntity<List<Item>> getBloods(){
		logger.info("List of all bloods");
		List<Item> items = service.getAllBlood();
		if(items.isEmpty()) {
			logger.error("Could not find any blood");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}
	
	/** 
	    * Get bloods by type. 
	    * @param none.  
	    * @return ResponseEntity<List<Item>>. 
	    */
	@RequestMapping(value="/item/blood/type/{bloodtype}", method = RequestMethod.GET)
	public ResponseEntity<List<Item>> getBloodByType(String bloodtype){
		logger.info("Get Blood of Type {}", bloodtype);
		List<Item> items = service.getBloodByType(bloodtype);
		if(items.isEmpty()) {
			logger.error("Could not find blood with type {}", bloodtype);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}
	
	/** 
	    * Search medicine by catalog. 
	    * @param none.  
	    * @return ResponseEntity<List<Item>>. 
	    */
	@RequestMapping(value="/item/medicine/catalogs/{catalog}", method = RequestMethod.GET)
	public ResponseEntity<List<Item>> searchMedicineByCatalog(String catalog){
		logger.info("Get medicine of {}", catalog);
		List<Item> items = service.searchMedicineByCatalog(catalog);
		if(items.isEmpty()) {
			logger.error("Could not find medicine of {}", catalog);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}
	
	/**
	    * Get medicine by catalog
	    * @param catalog
	    * @return ResponseEntity 
	    * 
	    */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/item/medicine/catalog/{catalog}", method = RequestMethod.GET)
	public ResponseEntity<List<Item>> getMedicineByCatalog(String catalog){
		logger.info("Get medicine of {}", catalog);
		List<Item> items = service.getMedicineByCatalog(catalog);
		if(items.isEmpty()) {
			logger.error("Could not find medicine of {}", catalog);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}
	
	/** 
	    * Find items by store. 
	    * @param store.  
	    * @return ResponseEntity<List<Item>>. 
	    */
	@RequestMapping(value="/item/store/{email}", method = RequestMethod.GET)
	public ResponseEntity<List<Item>> getItemsByStore(@PathVariable("email") String email){
		logger.info("List of all items of {}", email);
		List<Item> items = service.getItemByStore(service.getStoreByEmail(email));
		if(items.isEmpty()) {
			logger.error("Could not find any item of {}", email);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}
	
	//--------------------------------------------------------------------------
	//----------------------Admin Operation ADMIN only--------------------------
	//--------------------------------------------------------------------------
	
	/** 
	    * Create new Store(register) -> insert store into store_list table. 
	    * @param store request body, Store in json form. 
	    * @param ucBuilder. 
	    * @return ResponseEntity<String>. 
	    */
	//@RequestMapping(value="/admin/store", method = RequestMethod.POST)
	//public ResponseEntity<?> createStore(@RequestBody Store store, UriComponentsBuilder ucBuilder){
	//	logger.info("Add Store {}", store);
	//	
		//TODO check
	//	service.saveStore(store);
	//	HttpHeaders headers = new HttpHeaders();
	//	headers.setLocation(ucBuilder.path("/store/{id}")
	//			.buildAndExpand(store.getStoreId()).toUri());
	//	return new ResponseEntity<String>(headers, HttpStatus.OK);
	//}
	
	/** 
	    * Update Store.
	    * @param store request body, Store in json form.
	    * @param id Id of store instance in database  
	    * @return ResponseEntity<Store>. 
	    */
	@RequestMapping(value="/admin/store/{id}", method=RequestMethod.PUT)
	public ResponseEntity<?> updateStore(@PathVariable("id") long id, @RequestBody Store store){
		logger.info("Updating Store with id {}", id);
		Store storeFromDB = service.getStoreById(id);
		
		if(storeFromDB == null) {
			logger.error("Unable to update. Store with id {} not find",id);
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		storeFromDB.setStoreName(store.getStoreName());
		storeFromDB.setAddress(store.getAddress());
		storeFromDB.setPhone(store.getPhone());
		storeFromDB.setWebSite(store.getWebSite());
		storeFromDB.setEmail(store.getEmail());
		service.saveStore(storeFromDB);
		
		return new ResponseEntity<Store>(store, HttpStatus.OK);
	}
	
	/** 
	    * Delete Store -> remove store with given id from store_list table. 
	    * @param id.  
	    * @return ResponseEntity<Store>. 
	    */
	@RequestMapping(value="/admin/store/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteStore(@PathVariable("id") long id){
		logger.info("Deleting Store with id {}", id);
		Store store = service.getStoreById(id);
		
		if(store == null) {
			logger.error("Unable to delete, store with id {} not found", id);
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		try {
			fileservice.deleteFile(store.getImagePath());
		} catch (Exception e) {
			logger.info("No such file");
		}
		service.deleteStoreById(id);
		return new ResponseEntity<Store>(HttpStatus.OK);
	}
	
	
	//-----------------------------------------------------------------------------------
	//--------------------------Item Management MANAGER only-----------------------------
	//-----------------------------------------------------------------------------------
	
	/** 
	    * Create new Item -> insert item into item_list table.
	    * @param tem request body, Item in json form. 
	    * @param ucBuilder. 
	    * @return ResponseEntity<String>. 
	    */
	@RequestMapping(value="/manager", method = RequestMethod.POST)
	public ResponseEntity<Item> createItem(@RequestHeader("Authorization") String tokenHeader, 
			@RequestBody Item item, UriComponentsBuilder ucBuilder){
		logger.info("Add Item {}", item);
		
		String jwtToken = tokenHeader.substring(7);
		String email = jwtTokenUtil.getUsernameFromToken(jwtToken);
		
		item.setStore(service.getStoreByEmail(email));
		Item savedItem = service.saveItem(item);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/manager/{id}")
				.buildAndExpand(item.getItemId()).toUri());
		return new ResponseEntity<Item>(savedItem, HttpStatus.OK);
	}
	
	/** 
	    * Update Item. 
	    * @param Item request body, Item in json form.
	    * @param id Id of item instance in database  
	    * @return ResponseEntity<Item>. 
	    */	
    @RequestMapping(value="/manager/{id}", method=RequestMethod.PUT) public
	ResponseEntity<?> updateItem(@PathVariable("id") long id, @RequestBody Item item){ 
    	logger.info("Updating item with id {}", id); Item itemFromDB =
	    service.getItemById(id);
	  
	    if(itemFromDB == null) {
	        logger.error("Unable to update. item with id {} not find",id); 
	        return new ResponseEntity(HttpStatus.NOT_FOUND); 
	    }
	    
	    itemFromDB.setItemName(item.getItemName());
		itemFromDB.setPrice(item.getPrice());
		itemFromDB.setProducer(item.getProducer());
		itemFromDB.setStorage(item.getStorage());
	    
	    service.saveItem(itemFromDB);
	  
	   //return new ResponseEntity<Item>(itemFromDB, HttpStatus.OK); 
	   return new ResponseEntity(HttpStatus.OK);   
    }
    
	/**
	 * Auto Get Item By Store
	 * @param null
	 * @return List<Item> 
	 * */
    @RequestMapping(value="/manager", method=RequestMethod.GET)
	public ResponseEntity<?> getStoreItem(@RequestHeader("Authorization") String tokenHeader){
		logger.info("Retrieve item from current store");
		
		String jwtToken = tokenHeader.substring(7);
		String email = jwtTokenUtil.getUsernameFromToken(jwtToken);
		
		List<Item> items = service.getItemByStore(service.getStoreByEmail(email));
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);		
	}
    
    
	/** 
	    * Delete Item -> remove item with given id from item_list table. 
	    * @param id.  
	    * @return ResponseEntity<Item>. 
	 * @throws Exception 
	    */
	@RequestMapping(value="/manager/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteItem(@PathVariable("id") long id) {
		logger.info("Deleting Item with id {}", id);
		Item item = service.getItemById(id);
		
		if(item == null) {
			logger.error("Unable to delete, item with id {} not found", id);
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		try {
			fileservice.deleteFile(item.getImagePath());
		} catch (Exception e) {
			logger.info("No such file");
		}
		service.deleteItem(id);
		return new ResponseEntity<Item>(HttpStatus.OK);
	}
	
	/**
	    * Get store by Header
	    * @param --
	    * @return ResponseEntity<Store>
	 */
	@RequestMapping(value="/mail/auto", method = RequestMethod.GET)
	public ResponseEntity<?> autoGetStore(@RequestHeader("Authorization") String tokenHeader){
		logger.info("Automatically retrivel user information from header");
		
		String jwtToken = tokenHeader.substring(7);
		String email = jwtTokenUtil.getUsernameFromToken(jwtToken);
		
		Store store = service.getStoreByEmail(email);
		
		return new ResponseEntity<Store>(store, HttpStatus.OK);
	}
	
	public ResponseEntity<?> newPassword(@RequestHeader("Authorization") String tokenHeader, 
			@RequestBody String password){
		logger.info("Reset Password");
		
		String jwtToken = tokenHeader.substring(7);
		String email = jwtTokenUtil.getUsernameFromToken(jwtToken);
		
		Store store = service.getStoreByEmail(email);
		store.setPassword(password);
		service.saveStore(store);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	//----------------------------------------------------------------------------
	//------------------------------This's not normal-----------------------------
	//----------------------------------------------------------------------------
	
}
