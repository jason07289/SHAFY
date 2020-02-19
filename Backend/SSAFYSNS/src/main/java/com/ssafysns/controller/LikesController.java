package com.ssafysns.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.Likes;
import com.ssafysns.model.dto.Notification;
import com.ssafysns.model.dto.Post;
import com.ssafysns.model.dto.PostLikes;
import com.ssafysns.model.service.CommentService;
import com.ssafysns.model.service.JwtService;
import com.ssafysns.model.service.LikesService;
import com.ssafysns.model.service.NotificationService;
import com.ssafysns.model.service.PostService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/likes")
@EnableAutoConfiguration
public class LikesController {
	
	@Autowired
	LikesService likesService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	JwtService jwtService;
	
	/**
	 * Likes CRUD
	 */
	// 게시글 좋아요 누르기
	@ApiOperation(value = "게시글 좋아요/취소")
	@PostMapping("/{pno}")
	public ResponseEntity<Map<String, Object>> insert(@PathVariable int pno) throws Exception {
		/**
		 * JWT 토큰 받아오기
		 */
		String jwtId = jwtService.get("userid");
		
		Post post = postService.search(pno).get();
		
		System.out.println("게시글 좋아요 누르기_ id: "+jwtId+", pno: "+pno);
		PostLikes post_likes = new PostLikes(jwtId, pno);
		
		boolean status = likesService.insert(post_likes);
		if(status) {
			//게시글 좋아요 알림 전송
			if(!post.getId().equals(jwtId)) {
				Notification notification =  new Notification(post.getId(), new Date(), pno, 1, 0);
				notificationService.insert(notification);
			}
			return handleSuccess("게시글 좋아요를 눌렀습니다.");
		} else {
			return handleSuccess("게시글 좋아요를 취소합니다.");
		}
	}
	
	// 댓글 좋아요 누르기
	@ApiOperation(value = "댓글 좋아요/취소")
	@PostMapping("/{pno}/{cno}")
	public ResponseEntity<Map<String, Object>> insert(@PathVariable int pno, @PathVariable int cno) throws Exception {
		/**
		 * JWT 토큰 받아오기
		 */
		// 알림 전송
		Comment comment = commentService.search(cno);
		String jwtId = jwtService.get("userid");
		Likes likes = new Likes(jwtId, pno, cno);
		
		boolean status = likesService.insert(likes);
		if(status) {
			//게시글 좋아요 알림 전송			
			Notification notification =  new Notification(comment.getId(), new Date(), pno, 3, 0);
			notificationService.insert(notification);
			return handleSuccess("게시글 좋아요를 눌렀습니다.");
		} else {
			return handleSuccess("게시글 좋아요를 취소합니다.");
		}
	}
	
	
	// (하나의 게시글)해당 게시글의 좋아요 목록 가져오기 - PostController
	// (뉴스피드) 여러개의 게시글 좋아요 목록 가져오기 - PostController
	/**
	 * Exception Handler
	 */
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
