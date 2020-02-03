package com.ssafysns.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafysns.interceptor.JwtInterceptor;
//@Configuration
public class WebConfig  implements WebMvcConfigurer{
	private static final String[] EXCLUDE_PATHS = {
            "/api/user/login",
            "/api/user/findPw",
            "/api/user/nickName/**",
            "/api/user/signUp",
            "/error/**"
    };//토큰인증을 제외할 path 입력해주세요

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
						.addPathPatterns("/**")
						.excludePathPatterns(EXCLUDE_PATHS);
    }
}
