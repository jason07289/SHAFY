package com.ssafysns.exception;

public class PasswordException2 extends RuntimeException{
	public PasswordException2() {
		
		super("로그인 에러");
		System.out.println("로그인 에러");
	}
	public PasswordException2(String msg) {
		super(msg);
		System.out.println(msg);
	}
}
