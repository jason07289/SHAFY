package com.ssafysns.model.service;

import com.ssafysns.model.dto.User;
import com.ssafysns.model.dto.UserForSNS;
import com.ssafysns.model.dto.UserSNS;

public interface UserSNSService {
	public Object SNSLogin(String email,String type);
	
	public boolean signUpWithSeq(UserForSNS userForSNS);
	
}
