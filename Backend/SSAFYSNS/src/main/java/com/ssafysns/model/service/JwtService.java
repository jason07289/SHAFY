package com.ssafysns.model.service;

import java.util.Map;

import com.ssafysns.exception.UnauthorizedException;

public interface JwtService {

	public <T> String create(String id, String nickName);

	public boolean isUsable(String token) throws UnauthorizedException;

	Map<String, String> get(String key) throws UnauthorizedException;


}
