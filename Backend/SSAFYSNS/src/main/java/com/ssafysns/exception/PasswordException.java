package com.ssafysns.exception;

public class PasswordException extends RuntimeException{
	public PasswordException() {
		
		super("로그인 에러");
		System.out.println("로그인 에러");
	}
	public PasswordException(String msg) {
		super(msg);
		System.out.println(msg);
	}
}
