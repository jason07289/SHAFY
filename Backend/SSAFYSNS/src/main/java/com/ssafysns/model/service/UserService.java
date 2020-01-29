package com.ssafysns.model.service;

import java.io.UnsupportedEncodingException;

import com.ssafysns.model.dto.User;

public interface UserService {
	public String login(String id, String pw);
	public boolean create(User user);
	public User findPW(String id, String name);
	public boolean changePW(String id, String pw, String newPW);
//	public List<User> searchAll();
//	public void insert(User user);
//	public void delete(String id);
//	public void update(User user);
	public boolean update(User user);
	public boolean nickNameCheck(String nickName);
	public boolean signOut(String id, String pw);
	public User MyInfo(String id);
}
