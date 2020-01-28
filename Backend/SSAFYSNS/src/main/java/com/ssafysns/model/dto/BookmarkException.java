package com.ssafysns.model.dto;

public class BookmarkException extends RuntimeException {
	public BookmarkException() {
		System.out.println("BookmarkException");
	}

	public BookmarkException(String msg) {
		System.out.println(msg);
	}
}
