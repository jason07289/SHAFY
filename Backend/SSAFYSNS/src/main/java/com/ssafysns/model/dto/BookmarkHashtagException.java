package com.ssafysns.model.dto;

public class BookmarkHashtagException extends RuntimeException {
	public BookmarkHashtagException() {
		System.out.println("BookmarkHashtag 처리 중 오류 발생");
	}
	public BookmarkHashtagException(String msg) {
		System.out.println(msg);
	}
}
