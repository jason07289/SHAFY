package com.ssafysns.model.service;

import com.ssafysns.model.dto.User;

public interface UserService {
	public boolean login(String id, String pw);
	public void create(User user);
//	public List<User> searchAll();
//	public void insert(User user);
//	public void delete(String id);
//	public void update(User user);
}
