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

import com.ssafysns.model.dto.UserForSNS;
import com.ssafysns.model.service.JwtService;
import com.ssafysns.model.service.UserSNSService;
import com.ssafysns.snsapi.GithubAPI;
import com.ssafysns.snsapi.GoogleAPI;
import com.ssafysns.snsapi.KakaoAPI;
import com.ssafysns.snsapi.NaverAPI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins="{*}", maxAge=6000)
@RestController
@Api(value="SSAFY SNS", description="SSAFY SNS")
@EnableAutoConfiguration
public class userSNSController {

	@Autowired
	private UserSNSService userSNSService;
	
	@Autowired
	private KakaoAPI kakao;
	
	@Autowired
	private NaverAPI naver;
	
	@Autowired
	private GithubAPI github;
	
		
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
		
		try {
			userSNSService.signUpWithSeq(userForSNS);
			
			return handleSuccess("가입 성공");
		} catch (Exception e) {
			e.printStackTrace();
			return handleFail("가입 실패", HttpStatus.OK);
		}
		
	}
	
	
    @RequestMapping(value="/KakaoLogin")
    public ResponseEntity<Map<String, Object>> klogin(@RequestParam("code") String code, HttpSession session) {
    	System.out.println("====================login=====================");
        String access_Token = kakao.getAccessToken(code);
        HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
        System.out.println("login Controller : " + userInfo);
        System.out.println("====================login=====================");
        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("snsid") != null) {
            session.setAttribute("userId", userInfo.get("snsid"));
            session.setAttribute("access_Token", access_Token);
        }
        
        String snsid = userInfo.get("snsid").toString();
        System.out.println(userInfo.get("snsid").toString());
        System.out.println(userInfo.get("nickname").toString());
        
        try {
			Object valueForReturn = userSNSService.SNSLogin(snsid, "kakao");
			
			if(valueForReturn instanceof String) {
				return handleSuccess("소셜 로그인 토큰 발급 완료.", valueForReturn.toString());
			}else if(valueForReturn instanceof Integer) {
				return handleSuccess(Integer.parseInt(valueForReturn.toString()));
			}else {
				return handleFail("리턴값이 String이나 Integer가 아닙니다.", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return handleFail("소셜 로그인 중 오류 발생", HttpStatus.OK);
		}
        
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
        if (userInfo.get("snsid") != null) {
            session.setAttribute("userId", userInfo.get("snsid"));
            session.setAttribute("access_Token", access_Token);
        }
        
        String snsid = userInfo.get("snsid").toString();
        System.out.println(userInfo.get("snsid").toString());
        System.out.println(userInfo.get("nickname").toString());
        
        try {
			Object valueForReturn = userSNSService.SNSLogin(snsid, "naver");
			
			if(valueForReturn instanceof String) {
				return handleSuccess("소셜 로그인 토큰 발급 완료.", valueForReturn.toString());
			}else if(valueForReturn instanceof Integer) {
				return handleSuccess(Integer.parseInt(valueForReturn.toString()));
			}else {
				return handleFail("리턴값이 String이나 Integer가 아닙니다.", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return handleFail("소셜 로그인 중 오류 발생", HttpStatus.OK);
		}
    }
    
    
    @RequestMapping(value="GithubLogin")
    public ResponseEntity<Map<String, Object>> glogin(@RequestParam("code") String code, HttpSession session) {
    	System.out.println("====================login=====================");
        String access_Token = github.getAccessToken(code);
        HashMap<String, Object> userInfo = github.getUserInfo(access_Token);
        System.out.println("getUserInfo 완료");
        System.out.println("login Controller : " + userInfo);
        System.out.println("====================login=====================");
        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("snsid") != null) {
            session.setAttribute("userId", userInfo.get("snsid"));
            session.setAttribute("access_Token", access_Token);
        }
        
        String snsid = userInfo.get("snsid").toString();
        System.out.println(userInfo.get("snsid").toString());
        System.out.println(userInfo.get("nickname").toString());
        
        try {
			Object valueForReturn = userSNSService.SNSLogin(snsid, "github");
			
			if(valueForReturn instanceof String) {
				return handleSuccess("소셜 로그인 토큰 발급 완료.", valueForReturn.toString());
			}else if(valueForReturn instanceof Integer) {
				return handleSuccess(Integer.parseInt(valueForReturn.toString()));
			}else {
				return handleFail("리턴값이 String이나 Integer가 아닙니다.", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return handleFail("소셜 로그인 중 오류 발생", HttpStatus.OK);
		}
    }
    
    @Autowired
    JwtService jwtService;
    
    @Autowired
    GoogleAPI google;
    
    
    @RequestMapping(value="GoogleLogin")
    public ResponseEntity<Map<String, Object>> gologin(@RequestParam("code") String code, HttpSession session) {
    	System.out.println("====================login=====================");
        String access_Token = google.getAccessToken(code);
        HashMap<String, Object> userInfo = google.getUserInfo(access_Token);
        System.out.println("getUserInfo 완료");
        System.out.println("login Controller : " + userInfo);
        System.out.println("====================login=====================");
        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("snsid") != null) {
            session.setAttribute("userId", userInfo.get("snsid"));
            session.setAttribute("access_Token", access_Token);
        }
        
        String snsid = userInfo.get("snsid").toString();
        System.out.println(userInfo.get("snsid").toString());
        System.out.println(userInfo.get("nickname").toString());
        
//        try {
//			Object valueForReturn = userSNSService.SNSLogin(snsid, "github");
//			
//			if(valueForReturn instanceof String) {
//				return handleSuccess("소셜 로그인 토큰 발급 완료.", valueForReturn.toString());
//			}else if(valueForReturn instanceof Integer) {
//				return handleSuccess(Integer.parseInt(valueForReturn.toString()));
//			}else {
//				return handleFail("리턴값이 String이나 Integer가 아닙니다.", HttpStatus.OK);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return handleFail("소셜 로그인 중 오류 발생", HttpStatus.OK);
//		}
        return handleSuccess(jwtService.create(snsid, snsid));
    }
//    GoogleLogin
    
}
