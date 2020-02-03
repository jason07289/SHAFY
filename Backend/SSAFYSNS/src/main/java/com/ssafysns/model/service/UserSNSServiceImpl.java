package com.ssafysns.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.User;
import com.ssafysns.model.dto.UserForSNS;
import com.ssafysns.model.dto.UserSNS;
import com.ssafysns.repository.UserRepository;
import com.ssafysns.repository.UserSNSRepository;

@Service
public class UserSNSServiceImpl implements UserSNSService {
	@Autowired
	UserSNSRepository userSNSRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtService jwtService;
	
	@Override
	public Object SNSLogin(String email, String type) {//usersns를 찾고 그에 맞는 user를  반환? 아님 바로 로그인 처리까지?
		UserSNS usns = userSNSRepository.findByEmailAndType(email, type);
		if(usns==null) {//새로운 회원 가입이 필요
			UserSNS newUserSNS = new UserSNS();
			newUserSNS.setEmail(email);
			newUserSNS.setType(type);
			userSNSRepository.save(newUserSNS);
			UserSNS find = userSNSRepository.findByEmailAndType(email, type);
			int seq = find.getSeq();
			return seq;//key값 seq
		}else {// 연동되어있는 id와 닉네임을 불러와서 토큰에 담아주고 반납?
			
			UserSNS find = userSNSRepository.findByEmailAndType(email, type);
			String id =find.getId();
			User findUser = userRepository.getOne(id);
			
			return jwtService.create(findUser.getId(), findUser.getNickname());
			//key값 토큰 컨트롤러에서 핸들러에 담음되겄다...
		}
	}

	@Override
	public boolean signUpWithSeq(UserForSNS userForSNS) {
		User user = new User(userForSNS.getId());
		
		
		
		
		return false;
	}
	
	

}
