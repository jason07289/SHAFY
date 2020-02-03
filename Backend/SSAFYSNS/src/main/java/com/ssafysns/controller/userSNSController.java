package com.ssafysns.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafysns.model.dto.User;
import com.ssafysns.model.dto.UserForSNS;
import com.ssafysns.model.service.JwtService;
import com.ssafysns.util.KakaoAPI;
import com.ssafysns.util.NaverAPI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins="{*}", maxAge=6000)
@RestController
@Api(value="SSAFY SNS", description="SSAFY SNS")
@EnableAutoConfiguration
public class userSNSController {

	@Autowired
	private KakaoAPI kakao;
	
	@Autowired
	private NaverAPI naver;
	
	@Autowired
	private JwtService jwtService;
		
    @RequestMapping(value="/kakao/")
    public String index() {
        
        return "index";
    }
    
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
    
	@ApiOperation("SNS로그인 시 회원가입할때, 기존이랑 같으나 userforSNS 객체엔 seq가 추가되어있다.")
	@PostMapping("/user/signUpWithSeq/")
	public ResponseEntity<Map<String, Object>> signUpWithSeq(@RequestBody UserForSNS userForSNS){
		
		
		
		return null;
	}
	
	
	
	
    @RequestMapping(value="/KakaoLogin")
    public ResponseEntity<Map<String, Object>> klogin(@RequestParam("code") String code, HttpSession session) {
    	System.out.println("====================login=====================");
        String access_Token = kakao.getAccessToken(code);
        HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
        System.out.println("login Controller : " + userInfo);
        System.out.println("====================login=====================");
        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_Token", access_Token);
        }
        
        System.out.println(userInfo.get("email").toString());
        System.out.println(userInfo.get("nickname").toString());
        
      
        
        return handleSuccess(jwtService.create(userInfo.get("email").toString(), userInfo.get("nickname").toString()));
    }
    
    
    @RequestMapping(value="NaverLogin")
    public ResponseEntity<Map<String, Object>> nlogin(@RequestParam("code") String code, HttpSession session) {
    	System.out.println("====================login=====================");
        String access_Token = naver.getAccessToken(code);
        HashMap<String, Object> userInfo = naver.getUserInfo(access_Token);
        System.out.println("getUserInfo 완료");
        System.out.println("login Controller : " + userInfo);
        System.out.println("====================login=====================");
        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_Token", access_Token);
        }
        
        System.out.println(userInfo.get("email").toString());
        System.out.println(userInfo.get("nickname").toString());
        
        
        
//        jwtService.create(userInfo.get("email").toString(), userInfo.get("nickname").toString());
        return handleSuccess(jwtService.create(userInfo.get("email").toString(), userInfo.get("nickname").toString()));
    }    
}
