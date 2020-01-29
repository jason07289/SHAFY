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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafysns.model.dto.Search;
import com.ssafysns.model.service.SearchService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/search")
@EnableAutoConfiguration
public class SearchController {
	@Autowired
	SearchService searchService;

	// 모든 search 조회
	@ApiOperation(value = "모든 search 목록 조회")
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> searchAll() throws Exception {
		return handleSuccess(searchService.searchAll());
	}

	// 일주일 이내의 search 조회
	@ApiOperation(value = "일주일 이내의 search 목록 조회")
	@GetMapping("")
	public ResponseEntity<Map<String, Object>> search() throws Exception {
		return handleSuccess(searchService.searchBySearchtime());
	}

	// Search 등록(no: Auto-increment, id: 로그인한 user의 id, hashtag: 검색한 해시태그 입력 필수!!!)
	@ApiOperation(value = "Search 등록(no: Auto-increment, id: 로그인한 user의 id, hashtag: 검색한 해시태그 입력 필수!!!)")
	@PostMapping
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Search search) throws Exception {
		// no는 auto-increment, id는 로그인한 유저의 id, hashtag만 param으로 받음

		// 1. id를 로그인한 유저의 id로 설정
		// search.setId("로그인한사용자의 id");

		searchService.insert(search);
		return handleSuccess("search 등록 완료");
	}

	// no에 해당하는 Search 삭제
	@ApiOperation(value = "no에 해당하는 Search 삭제")
	@DeleteMapping("/{no}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable int no) throws Exception {
		searchService.delete(no);
		return handleSuccess("Search 삭제 완료");
	}

	// datetime 이전에 해당하는 Search 삭제
	@ApiOperation(value = "datetime에 해당하는 Search 삭제")
	@DeleteMapping("/{datetime}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Date datetime) throws Exception {
		searchService.deleteBySearchtime(datetime);
		return handleSuccess("Search 삭제 완료");
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
