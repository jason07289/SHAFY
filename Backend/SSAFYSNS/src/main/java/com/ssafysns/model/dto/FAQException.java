package com.ssafysns.model.dto;

public class FAQException extends RuntimeException {
	public FAQException() {
		System.out.println("FAQException");
	}
	public FAQException(String msg) {
		System.out.println(msg);
	}
}
