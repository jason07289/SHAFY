package com.ssafysns.model.service;

import java.sql.SQLException;
import java.util.Optional;

import org.apache.ibatis.session.SqlSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.User;
import com.ssafysns.repository.UserRepository;
import com.ssafysns.util.AES256Util;
import com.ssafysns.util.MailUtil;
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


	@Override
	public User findPW(String id, String name)  {
		MailUtil mu = new MailUtil();
		System.out.println("findPW 들어왔어요");
		User find = userRepository.findByIdAndName(id, name);
		
		System.out.println("id와 이름으로 user를 찾았습니다.");
		String userEmail = find.getId();
		try {
			
			if(find==null||find.getDeleted()==1) {
				throw new SQLException("해당되는 회원정보가 없습니다.");
			}else {
				String key = mu.CreateKey();//임시비밀번호 생성부
				String subject = "[SSAFY SNS] 비밀번호 찾기 ";
				StringBuffer sbuff = new StringBuffer();
				sbuff.append("<div align='center' style='border:1px solid black; font-family:verdana'>")
				.append("<h3 style='font-size: 130%'> SSAFY SNS 임시 비밀번호를 안내해 드리겠습니다.</h3>")
				.append("<div style='font-size: 130%'> SSAFY SNS 임시 비밀번호는 <strong>")
				.append(key).append("</strong> 입니다.</div> <br/>");
			
				AES256Util aes = new AES256Util();
			
				System.out.println(find.toString());
				find.setPassword(aes.encrypt(key));
				System.out.println("save 직전");
				userRepository.save(find);
				System.out.println("save 직후");
			
				String msg = sbuff.toString();
			
				mu.sendMail(userEmail, subject, msg);
			
				return find;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
