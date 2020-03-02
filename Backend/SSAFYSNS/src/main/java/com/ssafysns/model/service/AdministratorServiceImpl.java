package com.ssafysns.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.User;
import com.ssafysns.repository.AdminRepository;
import com.ssafysns.repository.UserRepository;

@Service
public class AdministratorServiceImpl implements AdministratorService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AdminRepository adminRepository;

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

	@Override
	public String updateAuth(String auth, String id) {
//		String auth = user.getAuth();
//		String id = user.getId();
		try {
			adminRepository.updateAuth(id, auth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "사용자 권한을 조정하였습니다.";
	}

	@Override
	public String updateBanned(User user) {
		int banned = user.getBanned();
		String id = user.getId();
		try {
			adminRepository.updateBanned(id, banned);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "경고 등급을 조정하였습니다.";
	}
	
	
	
	
	
}
