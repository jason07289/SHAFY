package com.ssafysns.model.service;

import java.sql.SQLException;
import java.util.Optional;

import org.apache.ibatis.session.SqlSessionException;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.User;
import com.ssafysns.repository.UserRepository;
import com.ssafysns.util.AES256Util;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepository;
	
	@Override
//	@NotFound(action=NotFoundAction.IGNORE)
	public boolean create(User user) {
		try {
			AES256Util aes = new AES256Util();
			user.setPassword(aes.encrypt(user.getPassword()));
			
			Optional<User> find = userRepository.findById(user.getId());
			
			System.out.println(user.getId());
			System.out.println(user.getPassword());
			System.out.println("gggggggggggggggggg");
			System.out.println(find.isPresent());
			if(find.isPresent()) {
			if(find.get()!=null) {
				if(find.get().getDeleted()==0) {
					throw new SQLException("이미 등록된 아이디 입니다.");
					
				}else {
					userRepository.save(user);
					return true;
				}
				}
			}else {
				System.out.println("저장가능");
				userRepository.save(user);
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	@Override
	public boolean login(String id,String pw) {
		try {
			User user =userRepository.getOne(id);
			AES256Util aes = new AES256Util();
			
			if(user==null) {
				throw new SqlSessionException("등록되지 않은 회원입니다.");
			}else {
				if(user.getDeleted()==1) {
					throw new SqlSessionException("등록되지 않은 회원입니다.");
				}else {
					if(pw.equals(aes.decrypt(user.getPassword()))) {
						return true;
					}else {
						throw new SqlSessionException("비밀번호 오류");
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}


}
