package com.ssafysns.model.service;

import com.ssafysns.model.dto.UserForSNS;

public interface UserSNSService {
	public Object SNSLogin(String snsid,String type) throws Exception ;
	
	public boolean signUpWithSeq(UserForSNS userForSNS) throws Exception ;
	
}
