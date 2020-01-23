package com.xuefeng.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuefeng.config.JwtTokenUtil;
import com.xuefeng.model.JwtRequest;
import com.xuefeng.model.JwtResponse;
import com.xuefeng.model.Mail;
import com.xuefeng.model.Role;
import com.xuefeng.model.Store;
import com.xuefeng.services.EmailService;
import com.xuefeng.services.JwtUserDetailsService;
import com.xuefeng.services.MedicineService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUserDetailsService service;
	
	@Autowired
	private MedicineService medService;
	
	@Autowired
	private EmailService mailService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception{
		logger.info("Validating User Information");
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = service
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		//Return the roles to frant-end
		List<String> roles = new ArrayList<>();
		Store store = medService.getStoreByEmail(authenticationRequest.getUsername());
		for(Role role : store.getRoles()) {
			roles.add(role.getName());
		}
		
		logger.info("Login successfully");
		logger.info("Token: {}", token);
		
		return ResponseEntity.ok(new JwtResponse(token, roles));
	}
	
	@RequestMapping(value="/admin/createstore", method=RequestMethod.POST)
	public ResponseEntity<?> saveStoreUser(@RequestBody Store store) throws IOException, Exception{
		logger.info("Adding new Store account");
		//TODO check
		Mail mail = new Mail();
		mail.setMailFrom("server@medical.com");
		mail.setMailTo(store.getEmail());
		mail.setMailSubject("New Member");
		mail.setMailText("Password: "+store.getPassword());
		
		this.mailService.sendEmail(mail);
		
		service.save(store);
		
		return ResponseEntity.ok(HttpStatus.OK);
	} 
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
