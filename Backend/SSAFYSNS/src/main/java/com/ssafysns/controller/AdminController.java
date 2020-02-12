package com.ssafysns.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafysns.exception.UnauthorizedException;
import com.ssafysns.model.dto.User;
import com.ssafysns.model.service.AdministratorService;
import com.ssafysns.model.service.JwtService;
import com.ssafysns.model.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/admin")
@EnableAutoConfiguration
public class AdminController {
	
	@Autowired
	AdministratorService adminService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtService jwtService;
	
	@ApiOperation(value="[관리자] 전체 회원 조회")
	@PostMapping()
	public ResponseEntity<List<User>> searchAll() throws UnauthorizedException {
		List<User> user = null;
		String id = jwtService.get("userid");
		
		if(id.equals("forB@gmail.com")) {
			user = adminService.searchAll();
		} else {
//			return handleFail("관리자 권한입니다.", HttpStatus.OK);
		}
		
		return new ResponseEntity<List<User>>(user, HttpStatus.OK);		
	}
	
	@ApiOperation(value="[관리자] 회원 등급 조정")
	@GetMapping("/auth/{user}")
	public ResponseEntity<Map<String, Object>> updateUserAuth(@PathVariable User user) throws UnauthorizedException {
		
		String id = jwtService.get("userid");
		
		/**
		 * id로 User를 받아와서 관리자 등급을 확인 후 관리자 등급으로 체크
		 */
		if(id.equals("admin")) {
			userService.update(user);
		} else {
			return handleFail("등급 수정권한이 없습니다.", HttpStatus.OK);
		}
		
		return handleSuccess("회원등급을 조정하였습니다.");
	}
	
	@ApiOperation(value="[관리자] 회원 경고 누적")
	@GetMapping("/bann/{user}")
	public ResponseEntity<Map<String, Object>> updateUserBann(@PathVariable User user) throws UnauthorizedException {
		
		String id = jwtService.get("userid");
		
		/**
		 * id로 User를 받아와서 관리자 등급을 확인 후 관리자 등급으로 체크
		 */
		if(id.equals("admin")) {
			userService.update(user);
		} else {
			return handleFail("경고 수정권한이 없습니다.", HttpStatus.OK);
		}
		
		return handleSuccess("회원 경고를 조정하였습니다.");
	}
	
	/**
	 * ExceptionHandler
	 */
	@ExceptionHandler
	public ResponseEntity<Map<String, Object>> handler(Exception e) {
		return handleFail(e.getMessage(), HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> handleSuccess(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("message", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "fail");
		resultMap.put("message", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}


}