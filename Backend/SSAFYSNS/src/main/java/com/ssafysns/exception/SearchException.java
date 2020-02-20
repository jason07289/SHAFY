package com.ssafysns.exception;

public class SearchException extends RuntimeException {
	public SearchException() {
		System.out.println("Searching 처리 중 오류 발생");
	}
	public SearchException(String msg) {
		System.out.println(msg);
	}
}
