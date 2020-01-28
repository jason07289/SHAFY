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
import org.springframework.web.bind.annotation.RestController;

import com.ssafysns.model.dto.BookmarkHashtag;
import com.ssafysns.model.dto.FollowHashtag;
import com.ssafysns.model.dto.TabHashtag;
import com.ssafysns.model.service.BookmarkHashtagService;
import com.ssafysns.model.service.FollowHashtagService;
import com.ssafysns.model.service.TabHashtagService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
//@RequestMapping("/hashtag")
@EnableAutoConfiguration
public class HashtagController {
	@Autowired
	TabHashtagService tabHashtagService;
	@Autowired
	FollowHashtagService followHashtagService;
	@Autowired
	BookmarkHashtagService bookmarkHashtagService;

	// ***********************************************************************************************************
	// TabHashtag
	// 모든 TabHashtag 조회
	@ApiOperation(value = "모든 TabHashtag 목록 조회")
	@GetMapping("/tabtag/list")
	public ResponseEntity<Map<String, Object>> searchAllTabHashtag() throws Exception {
		return handleSuccess(tabHashtagService.searchAll());
	}

	// no에 해당하는 TabHashtag 조회
	@ApiOperation(value = "no에 해당하는 TabHashtag 조회")
	@GetMapping("/tabtag/{no}")
	public ResponseEntity<Map<String, Object>> searchTabHashtag(@PathVariable int no) throws Exception {
		return handleSuccess(tabHashtagService.search(no));
	}

	// id에 해당하는 TabHashtag 조회
	@ApiOperation(value = "id에 해당하는 TabHashtag 조회")
	@GetMapping("/tabtag/user/{id}")
	public ResponseEntity<Map<String, Object>> searchTabHashtag(@PathVariable String id) throws Exception {
		return handleSuccess(tabHashtagService.searchById(id));
	}

//	// TabHashtag 등록
//	@ApiOperation(value = "TabHashtag 등록")
//	@PostMapping("/tabtag")
//	public ResponseEntity<Map<String, Object>> insertTabHashtag(@RequestBody TabHashtag tabHashtag) throws Exception {
//		tabHashtagService.insert(tabHashtag);
//		return handleSuccess("TabHashtag 등록 완료");
//	}

	// TabHashtag 등록(no: Auto-increment, id: 로그인한 user의 id, hashtag: 입력 필수!!!)
	@ApiOperation(value = "TabHashtag 등록(no: Auto-increment, id: 로그인한 user의 id, hashtag: 입력 필수!!!)")
	@PostMapping("/tabtag")
	public ResponseEntity<Map<String, Object>> insertTabHashtag(@RequestBody TabHashtag tabHashtag) throws Exception {
		// no는 auto-increment, id는 로그인한 유저의 id, hashtag만 param으로 받음

		// 1. id를 로그인한 유저의 id로 설정
		// tabHashtag.setId("로그인한사용자의 id");

		tabHashtagService.insert(tabHashtag);
		return handleSuccess("TabHashtag 등록 완료");
	}

	// no에 해당하는 TabHashtag 삭제
	@ApiOperation(value = "no에 해당하는 TabHashtag 삭제")
	@DeleteMapping("/tabtag/{no}")
	public ResponseEntity<Map<String, Object>> deleteTabHashtag(@PathVariable int no) throws Exception {
		tabHashtagService.delete(no);
		return handleSuccess("TabHashtag 삭제 완료");
	}

	// id에 해당하는 TabHashtag 모두 삭제
	@ApiOperation(value = "id에 해당하는 TabHashtag 모두 삭제")
	@DeleteMapping("/tabtag/user/{id}")
	public ResponseEntity<Map<String, Object>> deleteTabHashtag(@PathVariable String id) throws Exception {
		tabHashtagService.deleteById(id);
		return handleSuccess("TabHashtag 삭제 완료");
	}

	// id에 해당하는 사용자의 hashtag에 해당하는 TabHashtag 삭제
	@ApiOperation(value = "id에 해당하는 사용자의 hashtag에 해당하는 TabHashtag 삭제")
	@DeleteMapping("/tabtag/user/{id}/hashtag/{hashtag}")
	public ResponseEntity<Map<String, Object>> deleteTabHashtag(@PathVariable String id, @PathVariable String hashtag)
			throws Exception {
		// id는 로그인한 유저의 id, hashtag만 param으로 받음 => 수정 예정!!!

		// 1. id를 로그인한 유저의 id로 설정
		// String id = "로그인한사용자의 id";

		tabHashtagService.deleteByIdAndHashtag(id, hashtag);
		return handleSuccess("TabHashtag 삭제 완료");
	}

	// TabHashtag 수정
	@ApiOperation(value = "TabHashtag 수정(Hashtag는 등록, 삭제만 있으므로 수정 기능은 삭제 예정)")
	@PutMapping("/tabtag")
	public ResponseEntity<Map<String, Object>> updateTabHashtag(@RequestBody TabHashtag tabHashtag) throws Exception {
		tabHashtagService.update(tabHashtag);
		return handleSuccess("TabHashtag 수정 완료");
	}

	// ***********************************************************************************************************
	// FollowHashtag
	// 모든 FollowHashtag 조회
	@ApiOperation(value = "모든 FollowHashtag 목록 조회")
	@GetMapping("/followtag/list")
	public ResponseEntity<Map<String, Object>> searchAllFollowHashtag() throws Exception {
		return handleSuccess(followHashtagService.searchAll());
	}

	// no에 해당하는 FollowHashtag 조회
	@ApiOperation(value = "no에 해당하는 FollowHashtag 조회")
	@GetMapping("/followtag/{no}")
	public ResponseEntity<Map<String, Object>> searchFollowHashtag(@PathVariable int no) throws Exception {
		return handleSuccess(followHashtagService.search(no));
	}

	// id에 해당하는 FollowHashtag 조회
	@ApiOperation(value = "id에 해당하는 FollowHashtag 조회")
	@GetMapping("/followtag/user/{id}")
	public ResponseEntity<Map<String, Object>> searchFollowHashtag(@PathVariable String id) throws Exception {
		return handleSuccess(followHashtagService.searchById(id));
	}

//	// FollowHashtag 등록
//	@ApiOperation(value = "FollowHashtag 등록")
//	@PostMapping("/followtag")
//	public ResponseEntity<Map<String, Object>> insertFollowHashtag(@RequestBody FollowHashtag followHashtag)
//			throws Exception {
//		followHashtagService.insert(followHashtag);
//		return handleSuccess("FollowHashtag 등록 완료");
//	}

	// FollowHashtag 등록(no: Auto-increment, id: 로그인한 user의 id, hashtag: 입력 필수!!!)")
	@ApiOperation(value = "FollowHashtag 등록(no: Auto-increment, id: 로그인한 user의 id, hashtag: 입력 필수!!!)")
	@PostMapping("/followtag")
	public ResponseEntity<Map<String, Object>> insertFollowHashtag(@RequestBody FollowHashtag followHashtag)
			throws Exception {
		// no는 auto-increment, id는 로그인한 유저의 id, hashtag만 param으로 받음

		// 1. id를 로그인한 유저의 id로 설정
		// tabHashtag.setId("로그인한사용자의 id");

		followHashtagService.insert(followHashtag);
		return handleSuccess("FollowHashtag 등록 완료");
	}

	// no에 해당하는 FollowHashtag 삭제
	@ApiOperation(value = "no에 해당하는 FollowHashtag 삭제")
	@DeleteMapping("/followtag/{no}")
	public ResponseEntity<Map<String, Object>> deleteFollowHashtag(@PathVariable int no) throws Exception {
		followHashtagService.delete(no);
		return handleSuccess("FollowHashtag 삭제 완료");
	}

	// id에 해당하는 FollowHashtag 모두 삭제
	@ApiOperation(value = "id에 해당하는 FollowHashtag 모두 삭제")
	@DeleteMapping("/followtag/user/{id}")
	public ResponseEntity<Map<String, Object>> deleteFollowHashtag(@PathVariable String id) throws Exception {
		followHashtagService.deleteById(id);
		return handleSuccess("FollowHashtag 삭제 완료");
	}

	// id에 해당하는 사용자의 hashtag에 해당하는 FollowHashtag 삭제
	@ApiOperation(value = "id에 해당하는 사용자의 hashtag에 해당하는 FollowHashtag 삭제")
	@DeleteMapping("/followtag/user/{id}/hashtag/{hashtag}")
	public ResponseEntity<Map<String, Object>> deleteFollowHashtag(@PathVariable String id,
			@PathVariable String hashtag) throws Exception {
		// id는 로그인한 유저의 id, hashtag만 param으로 받음 => 수정 예정!!!

		// 1. id를 로그인한 유저의 id로 설정
		// String id = "로그인한사용자의 id";

		followHashtagService.deleteByIdAndHashtag(id, hashtag);
		return handleSuccess("FollowHashtag 삭제 완료");
	}

	// FollowHashtag 수정
	@ApiOperation(value = "FollowHashtag 수정(Hashtag는 등록, 삭제만 있으므로 수정 기능은 삭제 예정)")
	@PutMapping("/followtag")
	public ResponseEntity<Map<String, Object>> updateFollowHashtag(@RequestBody FollowHashtag followHashtag)
			throws Exception {
		followHashtagService.update(followHashtag);
		return handleSuccess("FollowHashtag 수정 완료");
	}

	// ***********************************************************************************************************
	// BookmarkHashtag
	// 모든 BookmarkHashtag 조회
	@ApiOperation(value = "모든 BookmarkHashtag 목록 조회")
	@GetMapping("/bookmarktag/list")
	public ResponseEntity<Map<String, Object>> searchAllBookmarkHashtag() throws Exception {
		return handleSuccess(bookmarkHashtagService.searchAll());
	}

	// no에 해당하는 BookmarkHashtag 조회
	@ApiOperation(value = "no에 해당하는 BookmarkHashtag 조회")
	@GetMapping("/bookmarktag/{no}")
	public ResponseEntity<Map<String, Object>> searchBookmarkHashtag(@PathVariable int no) throws Exception {
		return handleSuccess(bookmarkHashtagService.search(no));
	}

	// id에 해당하는 BookmarkHashtag 조회
	@ApiOperation(value = "id에 해당하는 BookmarkHashtag 조회")
	@GetMapping("/bookmarktag/user/{id}")
	public ResponseEntity<Map<String, Object>> searchBookmarkHashtag(@PathVariable String id) throws Exception {
		return handleSuccess(bookmarkHashtagService.searchById(id));
	}

//	// BookmarkHashtag 등록
//	@ApiOperation(value = "BookmarkHashtag 등록")
//	@PostMapping("/bookmarktag")
//	public ResponseEntity<Map<String, Object>> insertBookmarkHashtag(@RequestBody BookmarkHashtag bookmarkHashtag)
//			throws Exception {
//		bookmarkHashtagService.insert(bookmarkHashtag);
//		return handleSuccess("BookmarkHashtag 등록 완료");
//	}

	// BookmarkHashtag 등록(no: Auto-increment, id: 로그인한 user의 id, hashtag: 입력 필수!!!)
	@ApiOperation(value = "BookmarkHashtag 등록(no: Auto-increment, id: 로그인한 user의 id, hashtag: 입력 필수!!!)")
	@PostMapping("/bookmarktag")
	public ResponseEntity<Map<String, Object>> insertBookmarkHashtag(@RequestBody BookmarkHashtag bookmarkHashtag)
			throws Exception {
		// no는 auto-increment, id는 로그인한 유저의 id, hashtag만 param으로 받음

		// 1. id를 로그인한 유저의 id로 설정
		// tabHashtag.setId("로그인한사용자의 id");

		bookmarkHashtagService.insert(bookmarkHashtag);
		return handleSuccess("BookmarkHashtag 등록 완료");
	}

	// no에 해당하는 BookmarkHashtag 삭제
	@ApiOperation(value = "no에 해당하는 BookmarkHashtag 삭제")
	@DeleteMapping("/bookmarktag/{no}")
	public ResponseEntity<Map<String, Object>> deleteBookmarkHashtag(@PathVariable int no) throws Exception {
		bookmarkHashtagService.delete(no);
		return handleSuccess("BookmarkHashtag 삭제 완료");
	}

	// id에 해당하는 BookmarkHashtag 모두 삭제
	@ApiOperation(value = "id에 해당하는 BookmarkHashtag 모두 삭제")
	@DeleteMapping("/bookmarktag/user/{id}")
	public ResponseEntity<Map<String, Object>> deleteBookmarkHashtag(@PathVariable String id) throws Exception {
		bookmarkHashtagService.deleteById(id);
		return handleSuccess("BookmarkHashtag 삭제 완료");
	}

	// id에 해당하는 사용자의 hashtag에 해당하는 BookmarkHashtag 삭제
	@ApiOperation(value = "id에 해당하는 사용자의 hashtag에 해당하는 BookmarkHashtag 삭제")
	@DeleteMapping("/bookmarktag/user/{id}/hashtag/{hashtag}")
	public ResponseEntity<Map<String, Object>> deleteBookmarkHashtag(@PathVariable String id,
			@PathVariable String hashtag) throws Exception {
		// id는 로그인한 유저의 id, hashtag만 param으로 받음 => 수정 예정!!!

		// 1. id를 로그인한 유저의 id로 설정
		// String id = "로그인한사용자의 id";

		bookmarkHashtagService.deleteByIdAndHashtag(id, hashtag);
		return handleSuccess("BookmarkHashtag 삭제 완료");
	}

	// BookmarkHashtag 수정
	@ApiOperation(value = "BookmarkHashtag 수정(Hashtag는 등록, 삭제만 있으므로 수정 기능은 삭제 예정)")
	@PutMapping("/bookmarktag")
	public ResponseEntity<Map<String, Object>> updateBookmarkHashtag(@RequestBody BookmarkHashtag bookmarkHashtag)
			throws Exception {
		bookmarkHashtagService.update(bookmarkHashtag);
		return handleSuccess("BookmarkHashtag 수정 완료");
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
