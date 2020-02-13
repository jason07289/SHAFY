package com.ssafysns.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.Notification;
import com.ssafysns.model.dto.NotificationException;
import com.ssafysns.model.dto.NotificationResult;
import com.ssafysns.model.dto.User;
import com.ssafysns.repository.NotificationRepository;
import com.ssafysns.repository.UserRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public void insert(Notification notification) {
		// 1. notification 테이블에 등록하고
		// 2. User 테이블의 alarm 컬럼을 1(true)로 변경
		try {
			// 1) user dto에 alarm 컬럼 만들고 default = 0으로 설정
			// 3) notification 등록
			notificationRepository.save(notification);
			// 5) -> user alarm을 무조건 true로 변경
			String id = notification.getId();
			Optional<User> find = userRepository.findById(id);
			find.ifPresent(user -> {
				user.setAlarm(1);
				userRepository.save(user);
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotificationException("Notification 등록 중 오류가 발생했습니다.");
		}
	}

//	@Override
//	public void delete(Date datetime) {
//		// 1. checked = true이고
//		// 2. datetime 이전이면(단, datetime을 일정시점(일단, 7일로 설정함) 이전으로 설정해야만 삭제할 수 있도록 함) 삭제
//		try {
//			notificationRepository.deleteByDatetime(datetime);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new NotificationException(datetime + "이전 기간에 해당하는 Notification 삭제 중 오류가 발생했습니다.");
//		}
//	}

	@Override
	public void update(Notification notification) {
		try {
			// 1. 알람 페이지에서 해당 알람을 확인하면, checked = 1
			int no = notification.getNo();
			notificationRepository.updateByChecked(no);
			// 2. -> 알람페이지에 들어왔으므로 무조건 User 테이블의 alarm 컬럼을 0(false)로 변경
			String id = notification.getId();
			Optional<User> find = userRepository.findById(id);
			find.ifPresent(user -> {
				user.setAlarm(0);
				userRepository.save(user);
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotificationException("Notification 수정 중 조회 중 오류가 발생했습니다.");
		}
	}

//	@Override
//	public List<Notification> searchAll() {
//		try {
//			return notificationRepository.findAll();
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new NotificationException("Notification 목록 조회 중 오류가 발생했습니다.");
//		}
//	}

	@Override
	public long count(String id) {
		try {
			return notificationRepository.countByIdAndNotChecked(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotificationException("로그인한 유저의 미확인 알람 개수 조회 중 오류가 발생했습니다.");
		}
	}

	@Override
	public boolean userAlarmCheck(String id) {
		try {
			int alarm = notificationRepository.selectUserAlarm(id);
			if (alarm == 0)
				return false;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotificationException("로그인한 유저의 알람 발생 여부 조회 중 오류가 발생했습니다.");
		}
	}

	@Override
	public List<Notification> searchAllByUserId(String id) {
		try {
			return notificationRepository.findAllByUserId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotificationException("Notification 목록 조회 중 오류가 발생했습니다.");
		}
	}

	@Override
	public List<NotificationResult> searchAll(String id) {
		try {
			List<NotificationResult> result = new ArrayList<NotificationResult>();
			List<Notification> findAll = searchAllByUserId(id);
			Notification notification;

			int pno;
			String notificationMessage = "";
			String comment = "";
			Date datetime;
			int checked;
			int state = -1;

			int size = findAll.size();
			for (int i = 0; i < size; ++i) {
				notification = findAll.get(i);

				pno = notification.getPno();
				comment = notification.getComment();
				datetime = notification.getDatetime();
				checked = notification.getChecked();
				state = notification.getState();

				// notificationMessage
				// 게시글 좋아요
				if (state == 1) {
					notificationMessage = "회원님의 게시물에 좋아요가 눌렸습니다.";
				}
				// 게시글 댓글
				else if (state == 2) {
					notificationMessage = "회원님의 게시물에 댓글이 달렸습니다. : " + comment;
				}
				// 댓글 좋아요
				else if (state == 3) {
					notificationMessage = "회원님의 댓글에 좋아요가 눌렸습니다.";
				}
				// 대댓글
				else if (state == 4) {
					notificationMessage = "회원님의 댓글에 대댓글이 달렸습니다. : " + comment;
				}

				result.add(new NotificationResult(pno, notificationMessage, comment, datetime, checked));
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotificationException("Notification 목록 조회 중 오류가 발생했습니다.");
		}
	}

}
