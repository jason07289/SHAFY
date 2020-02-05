package com.ssafysns.model.service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ssafysns.exception.UnauthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("jwtService")
public class JwtServiceImpl implements JwtService{

	private static final String SALT =  "ssafy3ban5jo";
	
	@Override
	public <T> String create(String id, String nickName, String auth){
		String jwt = Jwts.builder()
						 .setHeaderParam("typ", "JWT")
						 .setHeaderParam("alg", "HS256")
						 .setHeaderParam("regDate", System.currentTimeMillis())
						 //payload 부분
						 .claim("userid", id)
						 .claim("nickname", nickName)
						 .claim("auth", auth)
						 .signWith(SignatureAlgorithm.HS256, this.generateKey())
						 .compact();
			System.out.println("jwt 생성: "+jwt);
		return jwt;
	}	

	private byte[] generateKey(){
		byte[] key = null;
		try {
			key = SALT.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if(log.isInfoEnabled()){
				e.printStackTrace();
			}else{
				log.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}
		
		return key;
	}

	@Override
	public boolean isUsable(String jwt) throws UnauthorizedException {
		try{
			Jws<Claims> claims = Jwts.parser()
					  .setSigningKey(this.generateKey())
					  .parseClaimsJws(jwt);
			return true;
			
		}catch (Exception e) {
			throw new UnauthorizedException();
		}
	}
	
	@Override
	public String get(String key) throws UnauthorizedException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String jwtbearer = request.getHeader("Authorization");
		System.out.println("JWT getHeader: "+ jwtbearer);
		
		String[] strs=jwtbearer.split(" ");
		System.out.println(strs[0]);
		System.out.println(strs[1]);
		String jwt = strs[1];
		System.out.println("jwt split: "+jwt);
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser()
						 .setSigningKey(SALT.getBytes("UTF-8"))
						 .parseClaimsJws(jwt);
			
			System.out.println("this.isUsable(jwt): "+this.isUsable(jwt));
			System.out.println("get함수......................pass");
		} catch (Exception e) {
			throw new UnauthorizedException();
		}
		System.out.println(claims.getBody().get(key));
		
		return claims.getBody().get(key).toString();
	}

//	@Override
//	public HashMap<String, Object> getUserInfoGoogle(String jwt) throws UnauthorizedException {
//		System.out.println();
//        System.out.println();
//        
//        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
//        HashMap<String, Object> userInfo = new HashMap<>();
//        System.out.println("get userInfo id token(jwt): "+jwt);
//        	
//        Jws<Claims> claims = null;
//		try {
//			claims = Jwts.parser()
//						 .setSigningKey(SALT.getBytes("UTF-8"))
//						 .parseClaimsJws(jwt);
//			
//			System.out.println("this.isUsable(jwt): "+this.isUsable(jwt));
//			System.out.println("get함수......................pass");
//		} catch (Exception e) {
//			throw new UnauthorizedException();
//		}
//		System.out.println(claims.getBody());
//        
//        return null;
//	}	
	
	
	
}
