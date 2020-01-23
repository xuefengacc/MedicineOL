package com.xuefeng.services;

import javax.transaction.Transactional;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xuefeng.dao.RoleRepository;
import com.xuefeng.dao.StoreRepository;
import com.xuefeng.model.Role;
import com.xuefeng.model.Store;

@Service
@Transactional
public class JwtUserDetailsService implements UserDetailsService{

	@Autowired
	private StoreRepository repo;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	@Autowired
	private RoleRepository role;
	
//	private final Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		Store store = repo.findByEmail(username);
		
		if(store == null) {
			throw new UsernameNotFoundException("User not found with email "+username);
		}
		
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : store.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
		
		return new User(store.getEmail(), store.getPassword(),
				grantedAuthorities);
	}
	
	public Store save(Store store) {
		store.setPassword(bcryptEncoder.encode(store.getPassword()));
		if(store.getType().equals("MEDICALSTORE")) {
			store.setRoles(role.findByName("ROLE_MEDICAL"));	
		} else if(store.getType().equals("BLOODSTORE")) {
			store.setRoles(role.findByName("ROLE_BLOOD"));
		}
		
		return repo.save(store);
	}
}
