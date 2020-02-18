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

import com.ssafysns.model.dto.FavoritesHashtag;
import com.ssafysns.model.dto.FollowHashtag;
import com.ssafysns.model.dto.TabHashtag;
import com.ssafysns.model.service.FavoritesHashtagService;
import com.ssafysns.model.service.FollowHashtagService;
import com.ssafysns.model.service.JwtService;
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
	FavoritesHashtagService favoritesHashtagService;
	@Autowired
	JwtService jwtService;

	// ***********************************************************************************************************
	// TabHashtag
	// id에 해당하는 TabHashtag 조회
	@ApiOperation(value = "id에 해당하는 TabHashtag 조회")
	@GetMapping("/tabtag")
	public ResponseEntity<Map<String, Object>> searchTabHashtag() throws Exception {
		// 1. id를 로그인한 유저의 id로 설정
		System.out.println("아이디를 조회합니다");

		String id = jwtService.get("userid");

		System.out.println("아이디 조회 완료");

		return handleSuccess(tabHashtagService.searchById(id));
	}

	// TabHashtag 수정
	@ApiOperation(value = "TabHashtag 수정(insert, delete, update가 모두 update를 통해 이루어짐.) / hashtag만 입력해주세요!!! (no : X, id : X)")
	@PutMapping("/tabtag")
	public ResponseEntity<Map<String, Object>> updateTabHashtag(@RequestBody TabHashtag tabHashtag) throws Exception {
		// 1. id를 로그인한 유저의 id로 설정
		String id = jwtService.get("userid");
		tabHashtag.setId(id);

		tabHashtagService.update(tabHashtag);
		return handleSuccess("TabHashtag 수정 완료");
	}

	// 모든 TabHashtag 조회
//	@ApiOperation(value = "모든 TabHashtag 목록 조회")
//	@GetMapping("/tabtag/list")
//	public ResponseEntity<Map<String, Object>> searchAllTabHashtag() throws Exception {
//		return handleSuccess(tabHashtagService.searchAll());
//	}

//	// no에 해당하는 TabHashtag 조회
//	@ApiOperation(value = "no에 해당하는 TabHashtag 조회")
//	@GetMapping("/tabtag/{no}")
//	public ResponseEntity<Map<String, Object>> searchTabHashtag(@PathVariable int no) throws Exception {
//		return handleSuccess(tabHashtagService.search(no));
//	}

//	// TabHashtag 등록
//	@ApiOperation(value = "TabHashtag 등록")
//	@PostMapping("/tabtag")
//	public ResponseEntity<Map<String, Object>> insertTabHashtag(@RequestBody TabHashtag tabHashtag) throws Exception {
//		tabHashtagService.insert(tabHashtag);
//		return handleSuccess("TabHashtag 등록 완료");
//	}

//	// TabHashtag 등록(no: Auto-increment, id: 로그인한 user의 id, hashtag: 입력 필수!!!)
//	@ApiOperation(value = "TabHashtag 등록(no: Auto-increment, id: 로그인한 user의 id, hashtag: 입력 필수!!!)")
//	@PostMapping("/tabtag")
//	public ResponseEntity<Map<String, Object>> insertTabHashtag(@RequestBody TabHashtag tabHashtag) throws Exception {
//		// no는 auto-increment, id는 로그인한 유저의 id, hashtag만 param으로 받음
//
//		// 1. id를 로그인한 유저의 id로 설정 => 추후 주석 풀기
////		Map<String, Object> uid = jwtService.get("userid");
////		tabHashtag.setId(uid.get("userid").toString());
//
//		tabHashtagService.insert(tabHashtag);
//		return handleSuccess("TabHashtag 등록 완료");
//	}

//	// no에 해당하는 TabHashtag 삭제
//	@ApiOperation(value = "no에 해당하는 TabHashtag 삭제")
//	@DeleteMapping("/tabtag/{no}")
//	public ResponseEntity<Map<String, Object>> deleteTabHashtag(@PathVariable int no) throws Exception {
//		tabHashtagService.delete(no);
//		return handleSuccess("TabHashtag 삭제 완료");
//	}

//	// id에 해당하는 TabHashtag 모두 삭제
//	@ApiOperation(value = "id에 해당하는 TabHashtag 모두 삭제")
//	@DeleteMapping("/tabtag")
//	public ResponseEntity<Map<String, Object>> deleteTabHashtag() throws Exception {
//		// 1. id를 로그인한 유저의 id로 설정 => 추후 주석 풀기!!!
////		Map<String, Object> uid = jwtService.get("userid");
////		String id = uid.get("userid").toString();
//
//		// test시 직접 입력
//		String id = "";
//
//		tabHashtagService.deleteById(id);
//		return handleSuccess("TabHashtag 삭제 완료");
//	}

//	// id에 해당하는 사용자의 hashtag에 해당하는 TabHashtag 삭제
//	@ApiOperation(value = "id에 해당하는 사용자의 hashtag에 해당하는 TabHashtag 삭제")
//	@DeleteMapping("/tabtag/hashtag/{hashtag}")
//	public ResponseEntity<Map<String, Object>> deleteTabHashtag(@PathVariable String hashtag) throws Exception {
//		// 1. id를 로그인한 유저의 id로 설정 => 추후 주석 풀기!!!
////		Map<String, Object> uid = jwtService.get("userid");
////		String id = uid.get("userid").toString();
//
//		// test시 직접 입력
//		String id = "";
//
//		tabHashtagService.deleteByIdAndHashtag(id, hashtag);
//		return handleSuccess("TabHashtag 삭제 완료");
//	}

	// ***********************************************************************************************************
	// FollowHashtag

	// id에 해당하는 FollowHashtag 조회
	@ApiOperation(value = "id에 해당하는 FollowHashtag 조회")
	@GetMapping("/followtag")
	public ResponseEntity<Map<String, Object>> searchFollowHashtag() throws Exception {
		// 1. id를 로그인한 유저의 id로 설정
		String id = jwtService.get("userid");

		return handleSuccess(followHashtagService.searchById(id));
	}

	// FollowHashtag 수정
	@ApiOperation(value = "FollowHashtag 수정(insert, delete, update가 모두 update를 통해 이루어짐.) / hashtag만 입력해주세요!!! (no : X, id : X)")
	@PutMapping("/followtag")
	public ResponseEntity<Map<String, Object>> updateFollowHashtag(@RequestBody FollowHashtag followHashtag)
			throws Exception {
		// 1. id를 로그인한 유저의 id로 설정
		String id = jwtService.get("userid");
		followHashtag.setId(id);

		boolean result = followHashtagService.update(followHashtag);
		if (result)
			return handleSuccess("TabHashtag 수정 완료");
		else
			return handleFail("TabHashtag 20개 초과", HttpStatus.OK);
	}

	// 모든 FollowHashtag 조회
//	@ApiOperation(value = "모든 FollowHashtag 목록 조회")
//	@GetMapping("/followtag/list")
//	public ResponseEntity<Map<String, Object>> searchAllFollowHashtag() throws Exception {
//		return handleSuccess(followHashtagService.searchAll());
//	}

//	// no에 해당하는 FollowHashtag 조회
//	@ApiOperation(value = "no에 해당하는 FollowHashtag 조회")
//	@GetMapping("/followtag/{no}")
//	public ResponseEntity<Map<String, Object>> searchFollowHashtag(@PathVariable int no) throws Exception {
//		return handleSuccess(followHashtagService.search(no));
//	}

//	// FollowHashtag 등록
//	@ApiOperation(value = "FollowHashtag 등록")
//	@PostMapping("/followtag")
//	public ResponseEntity<Map<String, Object>> insertFollowHashtag(@RequestBody FollowHashtag followHashtag)
//			throws Exception {
//		followHashtagService.insert(followHashtag);
//		return handleSuccess("FollowHashtag 등록 완료");
//	}

//	// FollowHashtag 등록(no: Auto-increment, id: 로그인한 user의 id, hashtag: 입력 필수!!!)")
//	@ApiOperation(value = "FollowHashtag 등록(no: Auto-increment, id: 로그인한 user의 id, hashtag: 입력 필수!!!)")
//	@PostMapping("/followtag")
//	public ResponseEntity<Map<String, Object>> insertFollowHashtag(@RequestBody FollowHashtag followHashtag)
//			throws Exception {
//		// no는 auto-increment, id는 로그인한 유저의 id, hashtag만 param으로 받음
//
//		// 1. id를 로그인한 유저의 id로 설정
//		String id = jwtService.get("userid");
//		followHashtag.setId(id);
//
//		followHashtagService.insert(followHashtag);
//		return handleSuccess("FollowHashtag 등록 완료");
//	}

//	// no에 해당하는 FollowHashtag 삭제
//	@ApiOperation(value = "no에 해당하는 FollowHashtag 삭제")
//	@DeleteMapping("/followtag/{no}")
//	public ResponseEntity<Map<String, Object>> deleteFollowHashtag(@PathVariable int no) throws Exception {
//		followHashtagService.delete(no);
//		return handleSuccess("FollowHashtag 삭제 완료");
//	}

//	// id에 해당하는 FollowHashtag 모두 삭제
//	@ApiOperation(value = "id에 해당하는 FollowHashtag 모두 삭제")
//	@DeleteMapping("/followtag")
//	public ResponseEntity<Map<String, Object>> deleteFollowHashtag() throws Exception {
//		// 1. id를 로그인한 유저의 id로 설정
//		String id = jwtService.get("userid");
//
//		followHashtagService.deleteById(id);
//		return handleSuccess("FollowHashtag 삭제 완료");
//	}

//	// id에 해당하는 사용자의 hashtag에 해당하는 FollowHashtag 삭제
//	@ApiOperation(value = "id에 해당하는 사용자의 hashtag에 해당하는 FollowHashtag 삭제")
//	@DeleteMapping("/followtag/hashtag/{hashtag}")
//	public ResponseEntity<Map<String, Object>> deleteFollowHashtag(@PathVariable String hashtag) throws Exception {
//		// id는 로그인한 유저의 id, hashtag만 param으로 받음 => 수정 예정!!!
//
//		// 1. id를 로그인한 유저의 id로 설정
//		String id = jwtService.get("userid");
//
//		followHashtagService.deleteByIdAndHashtag(id, hashtag);
//		return handleSuccess("FollowHashtag 삭제 완료");
//	}

	// FollowHashtag 수정
//	@ApiOperation(value = "FollowHashtag 수정(Hashtag는 등록, 삭제만 있으므로 수정 기능은 삭제 예정)")
//	@PutMapping("/followtag")
//	public ResponseEntity<Map<String, Object>> updateFollowHashtag(@RequestBody FollowHashtag followHashtag)
//			throws Exception {
//		followHashtagService.update(followHashtag);
//		return handleSuccess("FollowHashtag 수정 완료");
//	}

	// ***********************************************************************************************************
	// FavoritesHashtag
	// 모든 FavoritesHashtag 조회
	@ApiOperation(value = "모든 FavoritesHashtag 목록 조회")
	@GetMapping("/favoritestag/list")
	public ResponseEntity<Map<String, Object>> searchAllFavoritesHashtag() throws Exception {
		return handleSuccess(favoritesHashtagService.searchAll());
	}

//	// no에 해당하는 FavoritesHashtag 조회
//	@ApiOperation(value = "no에 해당하는 FavoritesHashtag 조회")
//	@GetMapping("/favoritestag/{no}")
//	public ResponseEntity<Map<String, Object>> searchFavoritesHashtag(@PathVariable int no) throws Exception {
//		return handleSuccess(favoritesHashtagService.search(no));
//	}

	// id에 해당하는 FavoritesHashtag 조회
	@ApiOperation(value = "id에 해당하는 FavoritesHashtag 조회")
	@GetMapping("/favoritestag")
	public ResponseEntity<Map<String, Object>> searchFavoritesHashtag() throws Exception {
		// 1. id를 로그인한 유저의 id로 설정
		String id = jwtService.get("userid");

		return handleSuccess(favoritesHashtagService.searchById(id));
	}

//	// FavoritesHashtag 등록
//	@ApiOperation(value = "FavoritesHashtag 등록")
//	@PostMapping("/favoritestag")
//	public ResponseEntity<Map<String, Object>> insertFavoritesHashtag(@RequestBody FavoritesHashtag favoritesHashtag)
//			throws Exception {
//		favoritesHashtagService.insert(favoritesHashtag);
//		return handleSuccess("FavoritesHashtag 등록 완료");
//	}

	// FavoritesHashtag 등록(no: Auto-increment, id: 로그인한 user의 id, hashtag: 입력 필수!!!)
	@ApiOperation(value = "FavoritesHashtag 등록(no: Auto-increment, id: 로그인한 user의 id, hashtag: 입력 필수!!!)")
	@PostMapping("/favoritestag")
	public ResponseEntity<Map<String, Object>> insertFavoritesHashtag(@RequestBody FavoritesHashtag favoritesHashtag)
			throws Exception {
		// no는 auto-increment, id는 로그인한 유저의 id, hashtag만 param으로 받음

		// 1. id를 로그인한 유저의 id로 설정
		String id = jwtService.get("userid");
		favoritesHashtag.setId(id);

		favoritesHashtagService.insert(favoritesHashtag);
		return handleSuccess("FavoritesHashtag 등록 완료");
	}

//	// no에 해당하는 FavoritesHashtag 삭제
//	@ApiOperation(value = "no에 해당하는 FavoritesHashtag 삭제")
//	@DeleteMapping("/favoritestag/{no}")
//	public ResponseEntity<Map<String, Object>> deleteFavoritesHashtag(@PathVariable int no) throws Exception {
//		favoritesHashtagService.delete(no);
//		return handleSuccess("FavoritesHashtag 삭제 완료");
//	}

	// id에 해당하는 FavoritesHashtag 모두 삭제
	@ApiOperation(value = "id에 해당하는 FavoritesHashtag 모두 삭제")
	@DeleteMapping("/favoritestag")
	public ResponseEntity<Map<String, Object>> deleteFavoritesHashtag() throws Exception {
		// 1. id를 로그인한 유저의 id로 설정
		String id = jwtService.get("userid");

		favoritesHashtagService.deleteById(id);
		return handleSuccess("FavoritesHashtag 삭제 완료");
	}

	// id에 해당하는 사용자의 hashtag에 해당하는 FavoritesHashtag 삭제
	@ApiOperation(value = "id에 해당하는 사용자의 hashtag에 해당하는 FavoritesHashtag 삭제")
	@DeleteMapping("/favoritestag/hashtag/{hashtag}")
	public ResponseEntity<Map<String, Object>> deleteFavoritesHashtag(@PathVariable String hashtag) throws Exception {
		// id는 로그인한 유저의 id, hashtag만 param으로 받음 => 수정 예정!!!

		// 1. id를 로그인한 유저의 id로 설정
		String id = jwtService.get("userid");

		favoritesHashtagService.deleteByIdAndHashtag(id, hashtag);
		return handleSuccess("FavoritesHashtag 삭제 완료");
	}

//	// FavoritesHashtag 수정
//	@ApiOperation(value = "FavoritesHashtag 수정(Hashtag는 등록, 삭제만 있으므로 수정 기능은 삭제 예정)")
//	@PutMapping("/favoritestag")
//	public ResponseEntity<Map<String, Object>> updateFavoritesHashtag(@RequestBody FavoritesHashtag favoritesHashtag)
//			throws Exception {
//		favoritesHashtagService.update(favoritesHashtag);
//		return handleSuccess("FavoritesHashtag 수정 완료");
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
