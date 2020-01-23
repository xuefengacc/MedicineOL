package com.xuefeng.model;

import java.io.Serializable;
import java.util.List;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwtToken;
	private final List<String> roles;

	public JwtResponse(String jwtToken, List<String> roles) {
		this.jwtToken = jwtToken;
		this.roles = roles;
	}

	public String getJwtToken() {
		return this.jwtToken;
	}

	public List<String> getRoles() {
		return roles;
	}
	
}