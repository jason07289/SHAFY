package com.ssafysns.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.dto.User;
import com.ssafysns.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void create(User user) {
		try {
			
		} catch (Exception e) {

		}
	}

	
	@Override
	public boolean Login(String id) {
		
		
		return false;
	}


}
