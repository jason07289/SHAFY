package com.ssafysns.exception;

public class NotificationException extends RuntimeException {
	public NotificationException() {
		System.out.println("NotificationException");
	}
	public NotificationException(String msg) {
		System.out.println(msg);
	}
}
