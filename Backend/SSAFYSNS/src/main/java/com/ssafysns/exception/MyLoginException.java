package com.ssafysns.exception;

public class MyLoginException extends RuntimeException {
	public MyLoginException() {
		
		super("로그인 에러");
		System.out.println("로그인 에러");
	}
	public MyLoginException(String msg) {
		super(msg);
		System.out.println(msg);
	}
}