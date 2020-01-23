package com.ssafysns.model.service;

import com.ssafysns.model.dto.User;

public interface UserService {
	public boolean login(String id, String pw);
	public boolean create(User user);
	public User findPW(String id, String name);
	public boolean changePW(String id, String pw, String newPW);
//	public List<User> searchAll();
//	public void insert(User user);
//	public void delete(String id);
//	public void update(User user);
}
