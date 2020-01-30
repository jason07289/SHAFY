package com.ssafysns.controller;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafysns.model.dto.Likes;
import com.ssafysns.model.service.LikesService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/likes")
@EnableAutoConfiguration
public class LikesController {
	
	@Autowired
	LikesService likesService;
	
	/**
	 * Likes CRUD
	 */
	// 좋아요 누르기
	@ApiOperation(value = "좋아요 누르기")
	@PostMapping("/add/{pno}/{cno}")
	public ResponseEntity<Map<String, Object>> insert(@PathVariable int pno, @PathVariable int cno) throws Exception {
		String id="kimssafy";
		
		Likes likes = new Likes(id, pno, cno);
		likesService.insert(likes);
		return handleSuccess("좋아요를 눌렀습니다");
	}
	
	// 좋아요 취소
	@ApiOperation(value = "좋아요 취소")
	@DeleteMapping("cancel/{pno}/{cno}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable int pno, @PathVariable int cno) throws Exception {
		/**
		 * 작성자만 가능
		 */
		String id = "kimssafy";
		likesService.delete(id, pno, cno);
		return handleSuccess("좋아요 취소 완료");
	}
	
	
	// (하나의 게시글)해당 게시글의 좋아요 목록 가져오기
	@ApiOperation(value="게시글 하나의 좋아요 목록 가져오기")
	@GetMapping("/{pno}")
	public ResponseEntity<List<Likes>> searchByPno(@PathVariable int pno) throws Exception {
		
		List<Likes> likes = likesService.searchByPno(pno);
		
		return new ResponseEntity<List<Likes>>(likes, HttpStatus.OK);
	}
	
	// (뉴스피드) 여러개의 게시글 좋아요 목록 가져오기
	@ApiOperation(value="뉴스피드 게시글 전체 좋아요 목록 가져오기")
	@PostMapping("/all")
	public ResponseEntity<List<Likes>> searchByAllPno(@PathVariable List<Integer> pno) throws Exception {
			List<Likes> likes = likesService.searchByAllPno(pno);
			
			return new ResponseEntity<List<Likes>>(likes, HttpStatus.OK);
		}
	
	
	// 내가 좋아요 누른 글 가져오기
	@ApiOperation(value="내가 좋아요 누른 글 가져오기")
	@PostMapping("/me")
	public ResponseEntity<List<Integer>> searchById() throws Exception {
		String id = "kimssafy";
		List<Integer> likes = likesService.searchById(id); //좋아요 누른 글 리스트 리스트 반환
		
		return new ResponseEntity<List<Integer>>(likes, HttpStatus.OK);
	}
	
	
	//Exception Handler
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

}
