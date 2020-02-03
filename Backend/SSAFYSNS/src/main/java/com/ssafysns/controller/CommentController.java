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

import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins="{*}", maxAge=6000)
@RestController
@Api(value="SSAFY SNS", description="SSAFY SNS")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
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
	@PostMapping("/comment/list/{no}")
	public ResponseEntity<List<Comment>> findAllComments() throws Exception {
		return new ResponseEntity<List<Comment>>(commentService.searchPno(1), HttpStatus.OK);
	}
	
	@ApiOperation(value="id로 Comment 검색", response=Comment.class)
	@GetMapping("/comment/list/{id}")
	public ResponseEntity<List<Comment>> findById(@PathVariable String id) throws Exception {
		return new ResponseEntity<List<Comment>> (commentService.searchId(id), HttpStatus.OK);
	}
	
	@ApiOperation(value="Comment 작성")
	@PostMapping("/comment/new")
	public ResponseEntity<Map<String, Object>> commentInsert(@RequestBody Comment comment) throws Exception {
		
		String id = "JWTTokenID";
		
		commentService.insert(id, comment);
		return handleSuccess("댓글 등록 완료");
	}
	
	@ApiOperation(value="Comment update")
	@PostMapping("/comment/update")
	public ResponseEntity<Map<String, Object>> commentUpdate(@RequestBody Comment comment) throws Exception {
		commentService.update(comment);
		return handleSuccess("댓글 수정 완료");
	}
	
	@ApiOperation(value="no로 Comments 삭제")
	@GetMapping("/comment/delete/{no}")
	public ResponseEntity<Map<String, Object>> DeleteComment(@PathVariable int no) throws Exception {
		System.out.println("controller... no: "+no);
		commentService.delete(no);
		return handleSuccess("댓글 삭제 완료");
	}
	
}
