package com.xuefeng;
  
import java.util.ArrayList; 
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
  
import com.xuefeng.dao.RoleRepository; 
import com.xuefeng.dao.StoreRepository; 
import com.xuefeng.model.Role; 
import com.xuefeng.model.Store;
  
//@Component
public class CommandLineAppStartupRunner implements CommandLineRunner{
  
    @Autowired 
    StoreRepository repo;
  
    @Autowired 
    RoleRepository role;
    
	@Autowired
	private PasswordEncoder bcryptEncoder;
  
    @Override 
    public void run(String... args) throws Exception {
  
        //Initialize roles 
        Role admin = new Role("ROLE_ADMIN"); 
        Role medical = new Role("ROLE_MEDICAL");
        Role blood = new Role("ROLE_BLOOD");
        
        List<Role> roles = new ArrayList<>(); 
        roles.add(admin);
  
        role.save(admin); 
        role.save(medical);
        role.save(blood);
  
        //Initialize default admin 
        Store store = new Store();
        //store.setStoreId(100000); 
        store.setStoreName("Admin");
        store.setEmail("admin@medical.com");
        store.setPhone(123456); 
        store.setPassword(bcryptEncoder.encode("111111")); 
        store.setRoles(roles);
  
        store.setAddress(null); 
        store.setItems(null); 
        store.setType(null);
        store.setWebSite(null);
        
        if(repo.existsByEmail("admin@medical.com")) {
        	//Do nothing
        } else {
        	repo.save(store);	
        }
  
    } 
  
}
 