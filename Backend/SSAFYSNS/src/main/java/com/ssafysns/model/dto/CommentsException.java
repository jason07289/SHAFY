package com.ssafysns.model.dto;

public class CommentsException extends RuntimeException {
	public CommentsException() {
		System.out.println("CommentsException");
	}
	public CommentsException(String msg) {
		System.out.println(msg);
	}
}
