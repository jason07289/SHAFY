package com.ssafysns.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafysns.model.dto.Notice;
import com.ssafysns.model.service.NoticeService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/notice")
@EnableAutoConfiguration
public class NoticeController {
	@Autowired
	NoticeService noticeService;

	// 모든 Notice 조회
	@ApiOperation(value = "모든 Notice 목록 조회")
	@GetMapping("/searchAll")
	public ResponseEntity<Map<String, Object>> searchAll() throws Exception {
		return handleSuccess(noticeService.searchAll());
	}

	// no에 해당하는 Notice 조회
	@ApiOperation(value = "no에 해당하는 Notice 조회")
	@GetMapping("/search/{no}")
	public ResponseEntity<Map<String, Object>> search(@PathVariable int no) throws Exception {
		return handleSuccess(noticeService.search(no));
	}

	// Notice 등록
	@ApiOperation(value = "Notice 등록")
	@PostMapping("/insert")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Notice notice) throws Exception {
		noticeService.insert(notice);
		return handleSuccess("Notice 등록 완료");
	}

	// Notice 등록
	@ApiOperation(value = "Notice 삭제")
	@DeleteMapping("/delete/{no}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable int no) throws Exception {
		noticeService.delete(no);
		return handleSuccess("Notice 삭제 완료");
	}

	// Notice 수정
	@ApiOperation(value = "Notice 수정")
	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Notice notice) throws Exception {
		noticeService.update(notice);
		return handleSuccess("Notice 수정 완료");
	}

	@ExceptionHandler
	public ResponseEntity<Map<String, Object>> handler(Exception e) {
		return handleFail(e.getMessage(), HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> handleSuccess(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("data", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "fail");
		resultMap.put("data", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
