package com.ssafysns.exception;

public class VoteException extends RuntimeException {
	public VoteException() {
		System.out.println("VoteException");
	}
	public VoteException(String msg) {
		System.out.println(msg);
	}
}
