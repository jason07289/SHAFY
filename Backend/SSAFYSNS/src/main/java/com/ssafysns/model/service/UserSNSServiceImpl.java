package com.ssafysns.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.exception.MyLoginException;
import com.ssafysns.model.dto.User;
import com.ssafysns.model.dto.UserForSNS;
import com.ssafysns.model.dto.UserSNS;
import com.ssafysns.repository.UserRepository;
import com.ssafysns.repository.UserSNSRepository;
import com.ssafysns.util.AES256Util;

@Service
public class UserSNSServiceImpl implements UserSNSService {
	@Autowired
	UserSNSRepository userSNSRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtService jwtService;
	
	@Override
	public Object SNSLogin(String snsid, String type)throws Exception  {//usersns를 찾고 그에 맞는 user를  반환? 아님 바로 로그인 처리까지?
		UserSNS usns = userSNSRepository.findBySnsidAndType(snsid, type);
		if(usns==null) {//새로운 회원 가입이 필요
			UserSNS newUserSNS = new UserSNS();
			newUserSNS.setSnsid(snsid);
			newUserSNS.setType(type);
			userSNSRepository.save(newUserSNS);
			UserSNS find = userSNSRepository.findBySnsidAndType(snsid, type);
			Integer seq = find.getSeq();
			return seq;//key값 seq
		}else {// 연동되어있는 id와 닉네임을 불러와서 토큰에 담아주고 반납?
			if(usns.getId()==null) {//user_sns가 존재해도 연동이 되어있지 않은 경우.
				return usns.getSeq();
			}
			UserSNS find = userSNSRepository.findBySnsidAndType(snsid, type);
			String id =find.getId();
			User findUser = userRepository.getOne(id);
			
			return jwtService.create(findUser.getId(), findUser.getNickname(),findUser.getAuth());
			//key값 토큰 컨트롤러에서 핸들러에 담음되겄다...
		}
	}

	@Override
	public boolean signUpWithSeq(UserForSNS userForSNS) throws Exception {
		User user = new User(userForSNS.getId());
		UserSNS userSNS = userSNSRepository.getOne(userForSNS.getSeq());
		userSNS.setId(userForSNS.getId());
		
		user.setAlarm(userForSNS.getAlarm());
		user.setApproval(userForSNS.getApproval());
		user.setAuth(userForSNS.getAuth());
		user.setBanned(userForSNS.getBanned());
		user.setBirth(userForSNS.getBirth());
		user.setClass1(userForSNS.getClass1());
		user.setClass2(userForSNS.getClass2());
		user.setDeleted(userForSNS.getDeleted());
		user.setGrade(userForSNS.getGrade());
		user.setId(userForSNS.getId());
		user.setImg(userForSNS.getImg());
		user.setLocation(userForSNS.getLocation());
		user.setName(userForSNS.getName());
		user.setNickname(userForSNS.getNickname());
		user.setPassword(userForSNS.getPassword());
		user.setPhone(userForSNS.getPhone());
		user.setState(userForSNS.getState());
		user.setUtype(userForSNS.getUtype());
		
		userRepository.save(user);
		userSNSRepository.save(userSNS);
		return false;
	}

	@Override
	public boolean IntegrateWithId(UserForSNS userForSNS) throws Exception {
		String id = userForSNS.getId();
		String pw = userForSNS.getPassword();
		int seq = userForSNS.getSeq();
		User user =userRepository.getOne(id);
		
		AES256Util aes = new AES256Util();
		if(user==null) {
			throw new MyLoginException("등록되지 않은 회원입니다.");
		}else {
			if(user.getDeleted()==1) {
				throw new MyLoginException("등록되지 않은 회원입니다.");
			}else {
				if(pw.equals(aes.decrypt(user.getPassword()))) {
					//연동 진행
					UserSNS userSNS = userSNSRepository.getOne(seq);
					System.out.println("seq에 따른 user sns정보  "+userSNS);
					userSNS.setId(id);
					userSNSRepository.save(userSNS);
					return true;
					//연동 진행
				}else {
					throw new MyLoginException("비밀번호 오류");
				}
			}
		}
	
		
	}
	
	

}
