package com.ssafysns.model.service;

import java.util.HashMap;
import java.util.Map;

import com.ssafysns.exception.UnauthorizedException;

public interface JwtService {

	public <T> String create(String id, String nickName, String auth);

	public boolean isUsable(String token) throws UnauthorizedException;

	String get(String key) throws UnauthorizedException;

//	HashMap<String, Object> getUserInfoGoogle(String id_token) throws UnauthorizedException;
}
