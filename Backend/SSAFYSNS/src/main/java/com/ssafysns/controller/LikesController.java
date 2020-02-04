package com.ssafysns.controller;

import java.util.ArrayList;
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

import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.Likes;
import com.ssafysns.model.service.CommentService;
import com.ssafysns.model.service.JwtService;
import com.ssafysns.model.service.LikesService;
import com.ssafysns.repository.CommentRepository;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/likes")
@EnableAutoConfiguration
public class LikesController {
	
	@Autowired
	LikesService likesService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	JwtService jwtService;
	
	String jwtId = "kimssafy";
	
	/**
	 * Likes CRUD
	 */
	// 좋아요 누르기
	@ApiOperation(value = "좋아요 누르기")
	@PostMapping("/add/{pno}/{cno}")
	public ResponseEntity<Map<String, Object>> insert(@PathVariable int pno, @PathVariable int cno) throws Exception {
		/**
		 * JWT 토큰 받아오기
		 */
//		Map<String, Object> jwt = jwtService.get("userid");
//		String jwtId = jwt.get("userid").toString();
		
		Likes likes = new Likes(jwtId, pno, cno);
		likesService.insert(likes);
		return handleSuccess("좋아요를 눌렀습니다");
	}
	
	// 좋아요 취소
	@ApiOperation(value = "좋아요 취소")
	@DeleteMapping("cancel/{pno}/{cno}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable int pno, @PathVariable int cno) throws Exception {
		/**
		 * 좋아요를 눌렀던 사람만 가능
		 */
//		Map<String, Object> jwt = jwtService.get("userid");
//		String jwtId = jwt.get("userid").toString();
		
		likesService.delete(jwtId, pno, cno);
		return handleSuccess("좋아요 취소 완료");
	}
	
	
	// (하나의 게시글)해당 게시글의 좋아요 목록 가져오기 - PostController

	
	// (뉴스피드) 여러개의 게시글 좋아요 목록 가져오기 - PostController
	
	
	// 내가 좋아요 누른 게시글 @번호 가져오기
	@ApiOperation(value="내가 좋아요 누른 글 가져오기")
	@PostMapping("/me")
	public ResponseEntity<List<Integer>> searchById() throws Exception {
		String id = "kimssafy";
		List<Integer> likes = likesService.searchById(id); //좋아요 누른 글 리스트 리스트 반환
		
		return new ResponseEntity<List<Integer>>(likes, HttpStatus.OK);
	}
	
	
	
	
	
	
	/**
	 * Test
	 */
	@ApiOperation(value="테스트")
	@GetMapping("/test/{pno}")
	public ResponseEntity<List<Comment>> searchTest(@PathVariable int pno) throws Exception {
//		String id = "kimssafy";
//		List<Comment> comment = commentService.searchPno(pno);
//		System.out.println(comment.toString());
		List<Comment> cno_list = likesService.selectCno(pno);
		System.out.println("comment is====");
		System.out.println(cno_list.toString());
		for(int i = 0; i<cno_list.size(); i++) {
			cno_list.get(i).setLike(null);
			cno_list.get(i).setPost(null);
		}
		
		return new ResponseEntity<List<Comment>>(cno_list, HttpStatus.OK);
	}
	
	@ApiOperation(value="Boolean Test")
	@GetMapping("/test/likeTest")
	public ResponseEntity<List<Boolean>> searchTest() throws Exception {
		List<Integer> cno_list = new ArrayList<Integer>();
		cno_list.add(1);
		cno_list.add(3);
		cno_list.add(7);
		cno_list.add(12);
		List<Boolean> b_list = likesService.likeTest(cno_list);
		
		return new ResponseEntity<List<Boolean>>(b_list, HttpStatus.OK);
	}
	
	@ApiOperation(value="진짜 리얼 테스트")
	@GetMapping("/test/realTest")
	public ResponseEntity<List<Boolean>> realTest() throws Exception {
		List<Integer> cno_list = new ArrayList<Integer>();
		cno_list.add(1);
		cno_list.add(3);
		cno_list.add(7);
		cno_list.add(12);
		List<Boolean> b_list = likesService.likeTest(cno_list);
		
		return new ResponseEntity<List<Boolean>>(b_list, HttpStatus.OK);
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
