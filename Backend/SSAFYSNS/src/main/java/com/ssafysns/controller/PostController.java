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

import com.ssafysns.model.dto.Post;
import com.ssafysns.model.service.PostService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/post")
@EnableAutoConfiguration
public class PostController {
	@Autowired
	PostService postService;

	// 모든 Post 조회
	@ApiOperation(value = "모든 Post 목록 조회")
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> searchAll() throws Exception {
		return handleSuccess(postService.searchAll());
	}

	// pno에 해당하는 Post 조회
	@ApiOperation(value = "pno에 해당하는 Post 조회")
	@GetMapping("/{pno}")
	public ResponseEntity<Map<String, Object>> search(@PathVariable int pno) throws Exception {
		return handleSuccess(postService.search(pno));
	}

	// Post 등록
	@ApiOperation(value = "Post 등록")
	@PostMapping
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Post post) throws Exception {
		postService.insert(post);
		return handleSuccess("Post 등록 완료");
	}

	// pno에 해당하는 Post 삭제
	@ApiOperation(value = "pno에 해당하는 Post 삭제")
	@DeleteMapping("/{pno}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable int pno) throws Exception {
		postService.delete(pno);
		return handleSuccess("Post 삭제 완료");
	}

	// Post 수정
	@ApiOperation(value = "Post 수정")
	@PutMapping
	public ResponseEntity<Map<String, Object>> update(@RequestBody Post post) throws Exception {
		postService.update(post);
		return handleSuccess("Post 수정 완료");
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
