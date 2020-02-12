package com.ssafysns.exception;

public class AdminException extends RuntimeException{
	public AdminException() {
		
		super("로그인한 계정에 관리자 권한이 없습니다.");
		System.out.println("로그인한 계정에 관리자 권한이 없습니다.");
	}
	public AdminException(String msg) {
		super(msg);
		System.out.println(msg);
	}
}
