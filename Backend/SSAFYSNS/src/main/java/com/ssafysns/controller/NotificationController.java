package com.ssafysns.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafysns.model.dto.Notification;
import com.ssafysns.model.service.JwtService;
import com.ssafysns.model.service.NotificationService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/notification")
@EnableAutoConfiguration
public class NotificationController {
	@Autowired
	NotificationService notificationService;
	@Autowired
	JwtService jwtService;

	/*
	 * 1. comments와 like의 insert 시, controller에서 notification 발생시키기
	 * 
	 * - Controller에서
	 * 
	 * 1. 상단에
	 * 
	 * ```
	 * 
	 * @Autowired NotificationService notificationService; ```
	 * 
	 * 2. POST 메서드 안에서
	 * 
	 * Notification 발생시킴 Notification notification = new Notification(likes.getId(),
	 * new Date(), cno OR pno, 좋아요: 1 OR 댓글: 2 OR 대댓글: 3, 0);
	 * notificationService.insert(notification);
	 */

	// 1. checked = true이고
	// 2. datetime 이전이면(단, datetime을 일정시점(일단, 7일로 설정함) 이전으로 설정해야만 삭제할 수 있도록 함) 삭제
	@ApiOperation(value = "checked=true이고, datetime 이전에 해당하는 Notification 삭제")
	@DeleteMapping("/{datetime}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Date datetime) throws Exception {
		notificationService.delete(datetime);
		return handleSuccess("Notification 삭제 완료");
	}

	// checked=true로 수정
	@ApiOperation(value = "Notification cheked=true로 수정")
	@PutMapping
	public ResponseEntity<Map<String, Object>> update(@RequestBody Notification notification) throws Exception {
		notificationService.update(notification);
		return handleSuccess("Notification 수정 완료");
	}

	// 모든 Notification 조회
	@ApiOperation(value = "모든 Notification 목록 조회")
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> searchAll() throws Exception {
		notificationService.SSAFY("aaa");
		
		
		return handleSuccess("");
//		return handleSuccess(notificationService.searchAll());
	}

	// 로그인한 사용자의 Notification 개수 조회
	@ApiOperation(value = "로그인한 사용자의 Notification 개수 조회")
	@GetMapping("/count")
	public ResponseEntity<Map<String, Object>> count() throws Exception {
		/// 1. id를 로그인한 유저의 id로 설정
		String id = jwtService.get("userid");

		return handleSuccess(notificationService.count(id));
	}

	// 로그인한 사용자의 알람 발생 여부 조회
	@ApiOperation(value = "로그인한 사용자의 알람 발생 여부 조회")
	@GetMapping("/user/alarm")
	public ResponseEntity<Map<String, Object>> alarmcheck() throws Exception {
		// 1. id를 로그인한 유저의 id로 설정
		String id = jwtService.get("userid");
		boolean res = notificationService.userAlarmCheck(id);
		return handleSuccess(res ? "새로운 알람이 발생했습니다" : "새로운 알람이 발생하지 않았습니다.");
	}

	@ExceptionHandler
	public ResponseEntity<Map<String, Object>> handler(Exception e) {
		return handleFail(e.getMessage(), HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> handleSuccess(Object message) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("message", message);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> handleFail(Object message, HttpStatus status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "fail");
		resultMap.put("message", message);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
