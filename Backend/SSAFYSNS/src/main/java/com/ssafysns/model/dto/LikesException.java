package com.ssafysns.model.dto;

public class LikesException extends RuntimeException {
	public LikesException() {
		System.out.println("LikesException");
	}
	public LikesException(String msg) {
		System.out.println(msg);
	}
}
