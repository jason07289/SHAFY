package com.ssafysns.model.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.Notification;
import com.ssafysns.model.dto.NotificationException;
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

	@Override
	public void delete(Date datetime) {
		// 1. checked = true이고
		// 2. datetime 이전이면(단, datetime을 일정시점(일단, 7일로 설정함) 이전으로 설정해야만 삭제할 수 있도록 함) 삭제
		try {
			notificationRepository.deleteByDatetime(datetime);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotificationException(datetime + "이전 기간에 해당하는 Notification 삭제 중 오류가 발생했습니다.");
		}
	}

	@Override
	public void update(Notification notification) {
		try {
			// 1. 알람 페이지에서 해당 알람을 확인하면, checked = 1
			int no = notification.getNo();
			notificationRepository.updateByChecked(no, 1);
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

	@Override
	public List<Notification> searchAll() {
		try {
			return notificationRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotificationException("Notification 목록 조회 중 오류가 발생했습니다.");
		}
	}

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
	
	public void SSAFY(String id) {
		try {
			// 1. 쿠키를 저장하고 csrf token 따기
			String[] csrfExecute = { "/home/ubuntu/csrf.sh" }; // 쉘 파일에 cmd 명령어 넣고 쉘 파일을 실행 -> 여기서 _csrf 토큰 값 받아오기 (String 쪼개서)
			Process p = Runtime.getRuntime().exec(csrfExecute);
			String userId = id;

			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
			String line = null;

			String csrf = "";
			boolean isEnd = true;
			while ((line = br.readLine()) != null) {
				if (line.contains("meta name=\"_csrf\"")) {

					String tmp = line.substring(line.lastIndexOf("meta name=\"_csrf\""));
					tmp = tmp.substring(tmp.lastIndexOf("content=\"") + "content=\"".length());
					// csrf 값이 한 줄에 걸쳐 나오지 않고 다음 줄로 이어지면
					if (!tmp.contains("/>")) {
						isEnd = false;
					}
					// csrf 값이 한 줄에 끝나면
					if (isEnd) {
						csrf += tmp.substring(0, tmp.indexOf("\""));
					}
					// 다음 줄로 넘어가면
					else {
						csrf += tmp;
					}
//					System.out.println(csrf);
				}
				if (!isEnd) {
					csrf += line.substring(0, line.indexOf("\""));
				}

				System.out.println(line);
			}
			System.out.println(csrf);
			// 2. 저장한 csrf token과 쿠키를 이용해서 페이지 리턴 받기
			// parameter1($1): userId,parameter2($2):csrf_token)
			String[] authenticafionExecute = { "/home/ubuntu/authentication.sh", userId, csrf };
			p = Runtime.getRuntime().exec(authenticafionExecute);

			br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
			line = null;

			boolean isCertified = true;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				if (line.contains("등록된 사용자 정보가 없습니다.")) {
					isCertified = false;
				} else if (line.contains("비밀번호가 일치하지 않습니다.")) {
					isCertified = true;
				}
			}
			if (isCertified)
				System.out.println("SSAFY인 인증 완료");
			else
				System.out.println("SSAFY인 인증 실패");
		} catch (Exception e) {

			System.out.println(e);

		}
	}

}
