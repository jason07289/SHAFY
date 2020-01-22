package com.ssafysns.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafysns.model.dto.Comments;
import com.ssafysns.model.service.CommentsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins="{*}", maxAge=6000)
@RestController
@Api(value="SSAFY SNS", description="SSAFY SNS")
public class CommentsController {
	
	@Autowired
	private CommentsService commentService;
	
	@ExceptionHandler
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	private ResponseEntity<Map<String, Object>> handleSuccess(Object data){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("data", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state",  "fail");
		resultMap.put("data",  data);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value="", response=List.class)
	@GetMapping("/comments/list/all")
	public ResponseEntity<List<Comments>> findAllComments() throws Exception {
		return new ResponseEntity<List<Comments>>(commentService.searchComments(), HttpStatus.OK);
	}
	
	@ApiOperation(value="id로 Comments 검색", response=Comments.class)
	@GetMapping("/comments/list/{id}")
	public ResponseEntity<List<Comments>> findById(@PathVariable String id) throws Exception {
		return new ResponseEntity<List<Comments>> (commentService.searchId(id), HttpStatus.OK);
	}
	
	@ApiOperation(value="Comment 작성")
	@PostMapping("/comments/new")
	public ResponseEntity<Map<String, Object>> commentInsert(@RequestBody Comments comment) throws Exception {
		commentService.insert(comment);
		return handleSuccess("댓글 등록 완료");
	}
	
	@ApiOperation(value="Comment update")
	@PostMapping("/comments/update")
	public ResponseEntity<Map<String, Object>> commentUpdate(@RequestBody Comments comment) throws Exception {
		commentService.update(comment);
		return handleSuccess("댓글 수정 완료");
	}
	
	@ApiOperation(value="no로 Comments 삭제")
	@GetMapping("/comments/delete/{no}")
	public ResponseEntity<Map<String, Object>> DeleteComment(@PathVariable int no) throws Exception {
		System.out.println("controller... no: "+no);
		commentService.delete(no);
		return handleSuccess("댓글 삭제 완료");
	}
//
//	@ApiOperation(value="새로운 FAQ 생성")
//	@PostMapping("/faqInsert")
//	public ResponseEntity<Map<String, Object>> userInsert(@RequestBody FAQ faq) throws Exception {
//		System.out.println("controller: " + faq.toString());
//		faqService.insert(faq);
//		return handleSuccess("등록 완료");
//	}
	
}