package com.ssafysns.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafysns.model.dto.User;
import com.ssafysns.model.service.UserService;

//import com.ssafysns.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins="{*}", maxAge=6000)
@RestController
@Api(value="SSAFY SNS", description="SSAFY SNS")
@EnableAutoConfiguration
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ExceptionHandler
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	private ResponseEntity<Map<String, Object>> handleSuccess(Object data){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("data", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state",  "fail");
		resultMap.put("data",  data);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation("회원가입")
	@PostMapping("/api/user/signUp/")
	public ResponseEntity<Map<String, Object>> signUp(@RequestBody User user){
		

		if(userService.create(user)) {
			
			return handleSuccess("회원가입에 성공하셨습니다.");
		}else {
			return handleSuccess("중복된 아이디입니다.");
		}
	}
	
	@ApiOperation("비밀번호 찾기")
	@PostMapping("/api/user/findPw")
	public ResponseEntity<Map<String, Object>> findPw(@RequestBody User user){
		//비밀번호 찾기 기능. 이름을 입력받아서 이메일로 임시번호를 전송하고 로그인하게 하는 방식
		userService.findPW(user.getId(), user.getName());
		return handleSuccess("이메일로 임시 비빌번호를 전송하였습니다.");
	}
	
	@ApiOperation("로그인")
	@PostMapping("/api/user/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody User user){
//		System.out.println(user.getId()+" ............  "+user.getPassword());
		
		if(userService.login(user.getId(), user.getPassword())) {
		
			return handleSuccess("로그인에 성공하셨습니다.");
		}else {
			return handleSuccess("비밀번호나 아이디를 다시 입력해주세요.");
		}
	}
	
	@ApiOperation("비밀번호 변경")
	public ResponseEntity<Map<String,Object>> changePW(@RequestBody User user,@RequestBody String newPW){
		if(userService.changePW(user.getId(), user.getPassword(),newPW)) {
			
			return handleSuccess("비밀번호 변경에 성공하셨습니다.");
		}else {
			return handleSuccess("이전 비밀번호를 다시 입력해주세요.");
		}
	}
	
	
}