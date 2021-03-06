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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafysns.model.dto.Bookmark;
import com.ssafysns.model.service.BookmarkService;
import com.ssafysns.model.service.JwtService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/bookmark")
@EnableAutoConfiguration
public class BookmarkController {
	@Autowired
	BookmarkService bookmarkService;
	@Autowired
	JwtService jwtService;

//	// id에 해당하는 Bookmark 목록 조회
//	@ApiOperation(value = "로그인한 유저의 Bookmark 목록 조회")
//	@GetMapping
//	public ResponseEntity<Map<String, Object>> search() throws Exception {
//		// 1. id를 로그인한 유저의 id로 설정
//		String id = jwtService.get("userid");
//
//		return handleSuccess(bookmarkService.searchById(id));
//	}

	// 로그인한 유저의 Bookmark의 pno 목록 조회
	@ApiOperation(value = "로그인한 유저의 Bookmark Pno 목록 조회")
	@GetMapping()
	public ResponseEntity<Map<String, Object>> searchPno() throws Exception {
		// 1. id를 로그인한 유저의 id로 설정
		String id = jwtService.get("userid");
		
		return handleSuccess(bookmarkService.searchPnoById(id));
	}

	// Bookmark 등록(no: Auto-increment, id: 로그인한 user의 id, pno: 입력 필수!!!)
	@ApiOperation(value = "Bookmark 등록(no: Auto-increment, id: 로그인한 user의 id, pno: 입력 필수!!!)")
	@PostMapping
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Bookmark bookmark) throws Exception {
		// 1. id를 로그인한 유저의 id로 설정
		String id = jwtService.get("userid");
		bookmark.setId(id);
		bookmarkService.insert(bookmark);

		return handleSuccess("Bookmark 등록 완료");
	}

	// id에 해당하는 Bookmark 삭제
	@ApiOperation(value = "id에 해당하는 Bookmark 삭제")
	@DeleteMapping
	public ResponseEntity<Map<String, Object>> delete() throws Exception {
		// 1. id를 로그인한 유저의 id로 설정
		String id = jwtService.get("userid");

		bookmarkService.deleteById(id);
		return handleSuccess("Bookmark 삭제 완료");
	}

	// id에 해당하는 사용자의 pno를 Bookmark에서 삭제
	@ApiOperation(value = "id에 해당하는 사용자의 pno를 Bookmark에서 삭제")
	@DeleteMapping("/post/{pno}")
	public ResponseEntity<Map<String, Object>> deleteByIdAndPno(@PathVariable int pno) throws Exception {
		// 1. id를 로그인한 유저의 id로 설정
		String id = jwtService.get("userid");

		bookmarkService.deleteByIdAndPno(id, pno);
		return handleSuccess("Bookmark 삭제 완료");
	}

//	// 모든 Bookmark 조회
//	@ApiOperation(value = "모든 Bookmark 목록 조회")
//	@GetMapping("/list")
//	public ResponseEntity<Map<String, Object>> searchAll() throws Exception {
//		return handleSuccess(bookmarkService.searchAll());
//	}

//	// no에 해당하는 Bookmark 조회
//	@ApiOperation(value = "no에 해당하는 Bookmark 조회")
//	@GetMapping("/{no}")
//	public ResponseEntity<Map<String, Object>> search(@PathVariable int no) throws Exception {
//		return handleSuccess(bookmarkService.search(no));
//	}

//	// no에 해당하는 Bookmark 삭제
//	@ApiOperation(value = "no에 해당하는 Bookmark 삭제")
//	@DeleteMapping("/{no}")
//	public ResponseEntity<Map<String, Object>> delete(@PathVariable int no) throws Exception {
//		bookmarkService.delete(no);
//		return handleSuccess("Bookmark 삭제 완료");
//	}

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
