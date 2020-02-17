package com.ssafysns.exception;

public class IdException extends Exception {
	public IdException() {
		
		super("이미 존재하는 ID 입니다.");
		System.out.println("이미 존재하는 ID 입니다.");
	}
	public IdException(String msg) {
		super(msg);
		System.out.println(msg);
	}
}