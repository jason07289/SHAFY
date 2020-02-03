package com.ssafysns.model.dto;

public class FollowHashtagException extends RuntimeException {
	public FollowHashtagException() {
		System.out.println("FollowHashtag 처리 중 오류 발생");
	}
	public FollowHashtagException(String msg) {
		System.out.println(msg);
	}
}
