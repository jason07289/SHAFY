package com.ssafysns.exception;

public class CommentException extends RuntimeException {
	public CommentException() {
		System.out.println("CommentException");
	}
	public CommentException(String msg) {
		System.out.println(msg);
	}
}
