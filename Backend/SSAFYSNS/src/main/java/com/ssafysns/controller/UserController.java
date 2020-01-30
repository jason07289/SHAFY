package com.ssafysns.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafysns.model.dto.User;
import com.ssafysns.model.dto.UserForChangePW;
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
	
//	@Autowired
//	private jwtse
	
	@ExceptionHandler
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	private ResponseEntity<Map<String, Object>> handleSuccess(Object data){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("message", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	private ResponseEntity<Map<String, Object>> handleSuccess(Object data,String token){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("message", data);
		resultMap.put("JWT",token);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state",  "fail");
		resultMap.put("message",  data);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation("회원가입 id 중복불가, 닉네임 중복불가 (확인 로직으로 확인이 필요), token 중복불가 - session 토큰이 들어갈 자리")
	@PostMapping("/api/user/signUp/")
	public ResponseEntity<Map<String, Object>> signUp(@RequestBody User user){
		

		if(userService.create(user)) {
			
			return handleSuccess("회원가입에 성공하셨습니다.");
		}else {
			return handleFail("다시 확인해주세요", HttpStatus.OK);
		}
	}
	
	@ApiOperation("비밀번호 찾기")
	@PutMapping("/api/user/findPw")
	public ResponseEntity<Map<String, Object>> findPw(@RequestBody User user){
		//비밀번호 찾기 기능. 이름을 입력받아서 이메일로 임시번호를 전송하고 로그인하게 하는 방식
		userService.findPW(user.getId(), user.getName());
		return handleSuccess("이메일로 임시 비빌번호를 전송하였습니다.");
	}
	
	@ApiOperation("로그인")
	@PostMapping("/api/user/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody User user){
//		System.out.println(user.getId()+" ............  "+user.getPassword());
		
		String jwt=userService.login(user.getId(), user.getPassword());
		if(jwt.equals("iderr")) {
			return handleFail("아이디를 찾을 수 없습니다.", HttpStatus.OK);
		}else if(jwt.equals("pwerr")){
			return handleFail("잘못된 비밀번호 입니다.", HttpStatus.OK);
		}else {
			return handleSuccess("로그인 성공",jwt);
		}
	}
	
	@ApiOperation("비밀번호 변경")
	@PutMapping("/api/user/changePW")
	public ResponseEntity<Map<String,Object>> changePW( 	
			@RequestBody UserForChangePW param){
		System.out.println("!!"+param);
		if(userService.changePW(param.getId(), param.getPassword(),param.getNewPassword())) {
			System.out.println(param.getNewPassword());
			return handleSuccess("비밀번호 변경에 성공하셨습니다.");
		}else {
			return handleFail("이전 비밀번호를 다시 입력해주세요.",HttpStatus.OK);
		}
	}
	
	@ApiOperation("user 업데이트. 상황에 맞게 파라미터를 넘겨주면 편하게 사용가능 *비밀번호는 이 URI로 절대 접근하지 말것*")
	@PutMapping("/api/user/update")
	public ResponseEntity<Map<String,Object>> update(@RequestBody User user){
		if(userService.update(user) ){
			return handleSuccess("회원정보 변경에 성공하셨습니다.");
		}else {
			return handleFail("회원정보 변경 실패",HttpStatus.OK);
		}
	}
	
	@ApiOperation("닉네임 중복확인")
	@GetMapping("/api/user/nickName/{nickName}")
	public ResponseEntity<Map<String,Object>> nickNameCheck(@PathVariable String nickName){
		if(userService.nickNameCheck(nickName) ){
			return handleSuccess("사용가능한 닉네임입니다.");
		}else {
			return handleFail("이미 사용중인 닉네임 입니다.",HttpStatus.OK);
		}
	}
	
	@ApiOperation("회원 탈퇴. 비밀번호 확인 후 탈퇴로직 동작")
	@PutMapping("/api/user/signOut")
	public ResponseEntity<Map<String,Object>> signOut(@RequestBody User user){
		if(userService.signOut(user.getId(),user.getPassword()) ){
			return handleSuccess("회원 탈퇴 성공");
		}else {
			return handleFail("비밀번호가 맞지 않습니다.",HttpStatus.OK);
		}
	}
	
	@ApiOperation("회원 정보 조회. 비밀번호 등은 복호화 하지 않고 제공할 것임, front에서 필요한 정보만 표기하도록 해야함")
	@GetMapping("/api/user/MyInfo/{id}")
	public ResponseEntity<Map<String,Object>> MyInfo(@PathVariable String id){
		User user = userService.MyInfo(id);
		System.out.println("Controller   "+user);
		return handleSuccess(user);
		
	}
	
}