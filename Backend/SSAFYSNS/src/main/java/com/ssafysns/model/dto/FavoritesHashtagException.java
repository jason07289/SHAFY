package com.ssafysns.model.dto;

public class FavoritesHashtagException extends RuntimeException {
	public FavoritesHashtagException() {
		System.out.println("FavoritesHashtag 처리 중 오류 발생");
	}
	public FavoritesHashtagException(String msg) {
		System.out.println(msg);
	}
}
