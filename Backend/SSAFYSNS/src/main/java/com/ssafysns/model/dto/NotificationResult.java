package com.ssafysns.model.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NotificationResult {
	private int no; // Notification의 no
	private int pno; // Post의 pno
	private String notificationMessage; // 알림메세지
	private String comment; // 댓글이 달린 경우 댓글 내용 담기
	private Date datetime;
	private int checked; // 알람 확인여부 0: false, 1: true

	public NotificationResult() {
	}

	public NotificationResult(int no, int pno, String notificationMessage, String comment, Date datetime, int checked) {
		super();
		this.no = no;
		this.pno = pno;
		this.notificationMessage = notificationMessage;
		this.comment = comment;
		this.datetime = datetime;
		this.checked = checked;
	}

}
