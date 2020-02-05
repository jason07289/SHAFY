package com.ssafysns.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.exception.MyLoginException;
import com.ssafysns.model.dto.User;
import com.ssafysns.repository.UserRepository;
import com.ssafysns.util.AES256Util;
import com.ssafysns.util.MailUtil;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtService jwtService;

	
	@Override
//	@NotFound(action=NotFoundAction.IGNORE)
	public boolean create(User user) throws Exception {
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
			
		return false;
	}

	
	
	
	
	
	
	
	
	@Override
	public String login(String id,String pw) throws Exception {
			User user =userRepository.getOne(id);
			AES256Util aes = new AES256Util();
			if(user==null) {
				throw new MyLoginException("등록되지 않은 회원입니다.");
			}else {
				if(user.getDeleted()==1) {
					throw new MyLoginException("등록되지 않은 회원입니다.");
				}else {
					if(pw.equals(aes.decrypt(user.getPassword()))) {
						String jwt =jwtService.create(user.getId(), user.getNickname(), user.getAuth());
						System.out.println("isUsable: "+ jwtService.isUsable(jwt));
//						jwtService.get("id");
//						Map<String, Object> uid = jwtService.get("userid");
//						Map<String, Object> nickname = jwtService.get("nickname");
//						System.out.println(uid.get("userid"));
						return jwt;
					}else {
						throw new MyLoginException("비밀번호 오류");
					}
				}
			}
	}
	       



	@Override
	public User findPW(String id, String name) throws Exception{
		MailUtil mu = new MailUtil();
		System.out.println("findPW 들어왔어요");
		User find = userRepository.findByIdAndName(id, name);
		
		System.out.println("id와 이름으로 user를 찾았습니다.");
		String userEmail = find.getId();
			
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
	}


	@Override
	public boolean changePW(String id, String pw, String newPW) {
		try {
			System.out.println("id "+id);
			System.out.println("pw "+pw);
			System.out.println("newpw "+newPW);
			User find =userRepository.getOne(id);
			AES256Util aes = new AES256Util();
			
			if(pw.equals(aes.decrypt(find.getPassword()))) {
				find.setPassword(aes.encrypt(newPW));
				userRepository.save(find);
				
				return true;
			}else {
				throw new SqlSessionException("비밀번호 오류");
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}


	@Override
	public boolean update(User user) {
		try {
			userRepository.save(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		return false;
	}


	@Override
	public boolean nickNameCheck(String nickName) {
		try {
			System.out.println(nickName);
			System.out.println(userRepository.findByNickname(nickName));
			if(userRepository.findByNickname(nickName)==null) {
				return true;
			}else {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}


	@Override
	public boolean signOut(String id, String pw) {
		try {
			User find = userRepository.getOne(id);
			AES256Util aes = new AES256Util();

			if(pw.equals(aes.decrypt(find.getPassword()))) {
				find.setBanned(1);
				userRepository.save(find);
				return true;
			}else {
				throw new SQLException("잘못된 비밀번호입니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return false;
	}


	@Override
	public User MyInfo() throws Exception {
		String userid =jwtService.get("userid");
		
		User user = userRepository.getOne(userid);
		System.out.println(user);
	
		return user;
		
	}

	@Override
	public List<User> list() throws Exception{
		
		return userRepository.findByDeletedIs(0);
	}

}
