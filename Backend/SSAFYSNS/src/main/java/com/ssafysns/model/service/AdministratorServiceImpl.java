package com.ssafysns.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.User;
import com.ssafysns.repository.UserRepository;

@Service
public class AdministratorServiceImpl implements AdministratorService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public List<User> searchAll() {
		List<User> users = null;
		
		try {
			users = userRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[관리자] 전체 회원 조회");
		}
		
		return users;
	}
	
	
	
	
	
}
