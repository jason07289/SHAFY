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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.Likes;
import com.ssafysns.model.dto.Post;
import com.ssafysns.model.dto.User;
import com.ssafysns.model.service.CommentService;
import com.ssafysns.model.service.FollowHashtagService;
import com.ssafysns.model.service.JwtService;
import com.ssafysns.model.service.LikesService;
import com.ssafysns.model.service.PostService;
import com.ssafysns.model.service.TabHashtagService;
import com.ssafysns.model.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/post")
@EnableAutoConfiguration
public class PostController {
	@Autowired
	PostService postService;

	@Autowired
	CommentService commentService;
	
	@Autowired
	LikesService likesService;
	
	@Autowired
	FollowHashtagService followService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TabHashtagService tabService;
	
	@Autowired
	JwtService jwtService;
	
	String jwtId = "kimssafy";
	

	/**
	 * [탭]
	 * - 한 개의 hashtag에 해당하는 모든 Post, Comment, Likes 조회
	 */
	@ApiOperation(value="[Tab] hashtag 하나에 해당하는 게시글들의 게시글+댓글+좋아요 전부 가져오기")
	@GetMapping("/hashtag/tab/{hashtag}")
	public ResponseEntity<List<Post>> getPostByOneHashtag(@PathVariable String hashtag) throws Exception {
		
		/*
		 * hashtag 탭 해시태그는 Frontend에서 보내줘야 한당
		 */
		
		// 해시태그에 해당하는 게시글 리스트 가져오기
		List<Post> post_list = searchPostByHash(hashtag);

		post_list = returnPost(post_list);
		
		return new ResponseEntity<List<Post>>(post_list, HttpStatus.OK);
	}
	
	/**
	 * [뉴스피드]
	 * - 여러개의 Hashtag에 해당하는 Post와 그에 해당하는 Comment, Likes 조회
	 */
	@ApiOperation(value="[리얼-Follow] 여러개의 Hashtag에 해당하는 게시글 리스트와 게시글+댓글+좋아요 정보 반환")
	@GetMapping("/hashtag/all")
	public ResponseEntity<List<Post>> searchHashtagComment() throws Exception {
		/**
		 * JWT 토큰으로 받아오기
		 */
//		Map<String, Object> jwt = jwtService.get("userid");
//		String jwtId = jwt.get("userid").toString();
		
		// Follow Hashtag를 가지고 있는 모든 게시글 [번호] 리스트 가져오기
		List<Integer> pno_by_all_hash = postService.followHashPno(jwtId);
		List<Post> post_list = postService.searchAllFollowList(pno_by_all_hash);
		
		post_list = returnPost(post_list);
		
		return new ResponseEntity<List<Post>>(post_list, HttpStatus.OK);
	}
	
	/**
	 * [공통 코드]
	 * - Comment, Likes 처리
	 */
	public List<Post> returnPost(List<Post> posts){
		List<Post> post_list = posts;
		
		// 로그인한 사용자가 누른 모든 좋아요 리스트 가져오기
		List<Integer> cno_list = likesService.selectCnoById(jwtId);
		System.out.println("cno_list is " + cno_list.toString());
		
		// 각 게시글에 해당하는 댓글 + 각 댓글에 해당하는 좋아요 리스트
		for(int i = 0, post_size = post_list.size(); i<post_size; i++) {
			Post post = post_list.get(i);
			post.setLike(null);
			post.setUser(null);

			int pno = post.getPno();
			System.out.println("pno is "+pno);
			
			// 게시글 익명 처리
			if(post.getAnonymous() == 1) {
				//post.setId("익명");			// 나중에 개인 식별 코드 컬럼으로 대체
				post.setNickname("익명");
			}
			
			List<Boolean> like_boolean_list = likesService.likeBooleanList(cno_list, pno);
			System.out.println("like_list is "+like_boolean_list.toString());
			
			// 해당 게시글(pno)에 달린 댓글 리스트
			List<Comment> temp_comments_list = post.getComment();
			System.out.println("댓글 사이즈: "+temp_comments_list.size());
			
			for(int j = 0, comm_size = temp_comments_list.size(); j<comm_size; j++) {
				Comment temp_comments = temp_comments_list.get(j);
				
				// StackOverflow 방지
				temp_comments.setPost(null);
				temp_comments.setLike_count(temp_comments.getLike().size());
				temp_comments.setLike(null);
				temp_comments.setUser(null);
				if(like_boolean_list != null && like_boolean_list.get(j)) {	//내가 좋아요를 누른 댓글일 경우
					temp_comments.setLike_check(true);
				}
				// 댓글 익명 처리
				if(temp_comments.getAnonymous() == 1) {
					//post.setId("익명");			// 나중에 개인 식별 코드 컬럼으로 대체
					temp_comments.setNickname("익명");
				}
			}
		}
		return post_list;
	}
	
	
	/**
	 * Post CRUD
	 */
	// DB테이블에 있는 모든 Post 조회
	@ApiOperation(value = "(DB테이블에 있는)모든 Post 목록 조회")
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> searchAll() throws Exception {
		List<Post> posts = postService.searchAll();
		for(int i = 0; i<posts.size(); i++) {
			
			List<Comment> comment_list = posts.get(i).getComment();
			for(int j = 0, size = comment_list.size(); j<size; j++) {
				posts.get(i).getComment().get(j).setPost(null);
			}
		}
		return handleSuccess(posts);
	}
	
	// Post 등록
	@ApiOperation(value = "Post 등록")
	@PostMapping
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Post post) throws Exception {
		String id = post.getId();
		String hashtag = post.getHashtag();
		
		User user = userService.MyInfo();
		String nickname = user.getNickname();
		
		post.setNickname(nickname);
		
		/**
		 * post.getId()와  Id가 다를 때 비교 해야 하나?
		 */
		//관리자가 아닌데 공지사항 히든태그를 사용할 경우 BAD_REQUEST
		if(!id.equals("admin") && hashtag.startsWith("__공지사항__")) {
			return handleFail("fail", HttpStatus.BAD_REQUEST);
		} else {
			postService.insert(id, post);
		}
		return handleSuccess("Post 등록 완료");
	}
	
	// Post 삭제(pno)
	@ApiOperation(value = "pno에 해당하는 Post 삭제")
	@DeleteMapping("/{pno}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable int pno) throws Exception {
		/**
		 * 작성자만 가능
		 */
//		Map<String, Object> jwt = jwtService.get("userid");
//		String jwtId = jwt.get("userid").toString();
		
		boolean msg = postService.delete(jwtId, pno);
		if(msg) {
			return handleSuccess("Post 삭제 완료");
		} else {
			return handleFail("게시글 삭제 권한이 없습니다.", HttpStatus.OK);
		}
	}
	
	// Post 수정
	@ApiOperation(value = "Post 수정")
	@PutMapping("")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Post post) throws Exception {
		/**
		 * 작성자만 가능
		 */
//		Map<String, Object> jwt = jwtService.get("userid");
//		String jwtId = jwt.get("userid").toString();
		
		if(post.getId() == jwtId) {
			postService.update(jwtId, post);
			return handleSuccess("게시글 수정 완료");
		} else {
			/**
			 * 여기서 return을 뭐를 해야 할까?
			 */
			return handleFail("게시글 수정 권한이 없습니다.", HttpStatus.OK);
//			return handleFail(new HashMap<String, String>().put("message", "수정권한이 없습니다."), HttpStatus.BAD_REQUEST);
		}

	}
	
	/**
	 * Pno로 게시글+댓글+좋아요 정보 조회
	 */
	@ApiOperation(value="{pno}에 해당하는 게시글, 댓글, 좋아요 조회")
	@GetMapping("/post/{pno}")
	public ResponseEntity<Map<String, Object>> searchByPno(@PathVariable int pno) throws Exception {
		Map<String, Object> pnoMap = new HashMap<String, Object>();
		
		Post post = search(pno);
		
		/**
		 * 1. 내가 게시글을 좋아요 눌렀는지
		 * 2. 게시글에 달린 좋아요가 몇 개인지?
		 */

		post.setLike(null);
		post.setUser(null);

		// 게시글 익명 처리
		if(post.getAnonymous() == 1) {
			//post.setId("익명");			// 나중에 개인 식별 코드 컬럼으로 대체
			post.setNickname("익명");
		}
		
		// 로그인한 사용자가 누른 모든 좋아요 리스트 가져오기
		List<Integer> cno_list = likesService.selectCnoById(jwtId);
		System.out.println("cno_list is " + cno_list.toString());

		// 해당 게시글(pno)에 달린 댓글 리스트
		List<Comment> temp_comments_list = post.getComment();
		System.out.println("댓글 사이즈: "+temp_comments_list.size());
		
		// 해당 게시글(pno)에 달린 댓글 중 내가 좋아요 눌렀는지 안눌렀는지 여부
		List<Boolean> like_boolean_list = likesService.likeBooleanList(cno_list, pno);
		System.out.println("like_list is "+like_boolean_list.toString());

		for(int j = 0, comm_size = temp_comments_list.size(); j<comm_size; j++) {
			Comment temp_comments = temp_comments_list.get(j);
			
			// StackOverflow 방지
			temp_comments.setPost(null);
			temp_comments.setLike_count(temp_comments.getLike().size());
			temp_comments.setLike(null);
			temp_comments.setUser(null);
			if(like_boolean_list != null && like_boolean_list.get(j)) {	//내가 좋아요를 누른 댓글일 경우
				temp_comments.setLike_check(true);
			}
			// 댓글 익명 처리
			if(temp_comments.getAnonymous() == 1) {
				//post.setId("익명");			// 나중에 개인 식별 코드 컬럼으로 대체
				temp_comments.setNickname("익명");
			}
		}
		
		
		return new ResponseEntity<Map<String, Object>>(pnoMap, HttpStatus.OK);
	}

	// pno에 해당하는 Post 조회 (함수)
	@ApiOperation(value = "pno에 해당하는 Post 조회")
	public Post search(int pno) throws Exception {
		return postService.search(pno).get();
	}

	// pno에 해당하는 CommentList 조회 (함수)
	public List<Comment> searchComments(int pno) throws Exception {
		List<Comment> comments = commentService.searchPno(pno);
		return comments;
	}
	
	
	
	/**
	 * 베스트 게시글
	 */
	@ApiOperation(value="베스트 게시글 20개의 해당하는 게시글들의 게시글+댓글+좋아요 전부 가져오기")
	@GetMapping("/hashtag/best")
	public ResponseEntity<List<Post>> estPost(@PathVariable String hashtag) throws Exception {
		
		/*
		 * hashtag 탭 해시태그는 Frontend에서 보내줘야 한당
		 */
		
		// 해시태그에 해당하는 게시글 리스트 가져오기
		List<Post> post_list = postService.searchBest20();

		post_list = returnPost(post_list);
		
		return new ResponseEntity<List<Post>>(post_list, HttpStatus.OK);
	}
	
	
	
	
	// 해시태그에 해당하는 Post 리스트 조회 (함수)
	public List<Post> searchPostByHash(String hashtag) throws Exception {
		List<Post> posts = postService.search(hashtag);
		return posts;
	}
	
	// 해시태그에 해당하는 Comment 리스트 조회(함수)
	@ApiOperation(value = "hashtag에 해당하는 Comment 조회")
	public List<Comment> searchCommentByHash(String hashtag) throws Exception {
		
		List<Comment> comments = commentService.joinPost(hashtag);

		//return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
		return comments;
	}

	
	
	/**
	 * Comment
	 */
	// Comment 작성
	@ApiOperation(value="Comment 작성")
	@PostMapping("/comment/new")
	public ResponseEntity<Map<String, Object>> commentInsert(@RequestBody Comment comment) throws Exception {
//		Map<String, Object> jwt = jwtService.get("userid");
//		String jwtId = jwt.get("userid").toString();
		
		commentService.insert(jwtId, comment);
		return handleSuccess("댓글 등록 완료");
	}
	
	// Comment 수정
	@ApiOperation(value="Comment update")
	@PostMapping("/comment/update")
	public ResponseEntity<Map<String, Object>> commentUpdate(@RequestBody Comment comment) throws Exception {
		/**
		 * 작성자만 가능
		 */
//		Map<String, Object> jwt = jwtService.get("userid");
//		String jwtId = jwt.get("userid").toString();
		
		if(comment.getId() == jwtId) {
			commentService.update(comment);
			return handleSuccess("댓글 수정 완료");
		} else {
			return handleSuccess("댓글 삭제 권한이 없습니다.");
		}
	}
	
	// Comment 삭제
	@ApiOperation(value="no로 Comments 삭제")
	@GetMapping("/comment/delete/{no}")
	public ResponseEntity<Map<String, Object>> DeleteComment(@PathVariable int no) throws Exception {
//		Map<String, Object> jwt = jwtService.get("userid");
//		String jwtId = jwt.get("userid").toString();

		/**
		 * 작성자만 가능
		 */
		Boolean msg = commentService.delete(jwtId, no);
		
		if(msg == true) {
			return handleSuccess("댓글 삭제 완료");
		} else {
			return handleSuccess("댓글 삭제 권한이 없습니다.");
		}
	}
	
	// [함수] - 하나의 게시글(pno)에 해당하는 댓글 리스트
	public List<Comment> OnePostComment(int pno) throws Exception {
		List<Comment> comments = commentService.searchPno(pno);
		return comments;
	}	
	
	// [함수] - 여러개의 게시글(pno 리스트)에 해당하는 댓글 리스트
	public List<Comment> AllPostComment(List<Integer> pnolist) throws Exception {
		List<Comment> comments = commentService.searchAllCommenetList(pnolist);
		return comments;
	}

	
	
	/**
	 * ExceptionHandler
	 */
	@ExceptionHandler
	public ResponseEntity<Map<String, Object>> handler(Exception e) {
		return handleFail(e.getMessage(), HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> handleSuccess(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("message", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "fail");
		resultMap.put("message", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
