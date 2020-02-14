package com.ssafysns.model.service;

import java.util.List;

import com.ssafysns.exception.AdminException;
import com.ssafysns.exception.UnauthorizedException;
import com.ssafysns.model.dto.User;

public interface UserService {
	public String login(String id, String pw) throws Exception;
	public boolean create(User user) throws Exception;
	public User findPW(String id, String name) throws Exception;
	public boolean changePW(String id, String pw, String newPW);
//	public List<User> searchAll();
//	public void insert(User user);
//	public void delete(String id);
//	public void update(User user);
	public boolean update(User user);
	public boolean nickNameCheck(String nickName);
	public boolean signOut(String id, String pw);
	public User MyInfo()throws Exception;
	public List<User> list() throws Exception;
	public String emailAuthCodeCreate(String id) throws Exception;
	public String userBan(String id) throws UnauthorizedException,AdminException;
}
