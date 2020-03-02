package com.ssafysns.model.service;

import java.util.List;

import com.ssafysns.model.dto.User;

public interface AdministratorService {
	
	public List<User> searchAll();
	
	public String updateAuth(String auth, String id);
	
	public String updateBanned(User user);
	
	
}
