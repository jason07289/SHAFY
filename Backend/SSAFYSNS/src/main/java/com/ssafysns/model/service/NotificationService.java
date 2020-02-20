package com.ssafysns.model.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ssafysns.model.dto.Notification;
import com.ssafysns.model.dto.NotificationResult;

public interface NotificationService {
	// 게시물에 좋아요,댓글 입력시 & 댓글에 좋아요, 댓글 입력시 Notification 등록
	public void insert(Notification notification);

//	public void delete(Date datetime);

	// 알림을 확인한 경우 Notification의 checked=true, User의 alarm=0으로 수정
	public void update(Notification notification);

//	public List<Notification> searchAll();

	public  Notification searchByNo(int no);

	public List<Notification> searchAllByUserId(String id);

	public List<NotificationResult> searchAll(String id);

	public long count(String id);

	public boolean userAlarmCheck(String id);

}
