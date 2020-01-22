package com.ssafysns.model.dto;

public class TabHashtagException extends RuntimeException {
	public TabHashtagException() {
		System.out.println("TabHashtag 처리 중 오류 발생");
	}
	public TabHashtagException(String msg) {
		System.out.println(msg);
	}
}
