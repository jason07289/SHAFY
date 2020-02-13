package com.ssafysns.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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

import com.ssafysns.exception.UnauthorizedException;
import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.Notification;
import com.ssafysns.model.dto.Post;
import com.ssafysns.model.dto.PostLikes;
import com.ssafysns.model.dto.User;
import com.ssafysns.model.service.CommentService;
import com.ssafysns.model.service.FollowHashtagService;
import com.ssafysns.model.service.JwtService;
import com.ssafysns.model.service.LikesService;
import com.ssafysns.model.service.NotificationService;
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
	NotificationService notificationService;
	
	@Autowired
	JwtService jwtService;
	

	/**
	 * [탭]
	 * - 한 개의 hashtag에 해당하는 모든 Post, Comment, Likes 조회
	 */
	@ApiOperation(value="[Tab] hashtag 하나에 해당하는 게시글들의 게시글+댓글+좋아요 전부 가져오기")
	@GetMapping("/tab/{hashtag}/{page}")
	public ResponseEntity<List<Post>> getPostByOneHashtag(@PathVariable String hashtag, @PathVariable int page) throws Exception {
		/*
		 * hashtag 탭 해시태그는 Frontend에서 보내줘야 함.
		 */
		page = Integer.max(0, page*20-1);
		
		System.out.println(hashtag);
		hashtag = "#"+hashtag+"#";
		System.out.println(hashtag);
		
		List<Post> post_list = searchPostByHash(hashtag, page);	//개수 제한
		List<Integer> pno_list = postService.searchPnoByHash(hashtag, page);	//개수 제한
		
		post_list = returnPost(post_list, pno_list);
		
		return new ResponseEntity<List<Post>>(post_list, HttpStatus.OK);
	}
	
	/**
	 * [뉴스피드]
	 * - 여러개의 Hashtag에 해당하는 Post와 그에 해당하는 Comment, Likes 조회
	 */
	@ApiOperation(value="[리얼-Follow] 여러개의 Hashtag에 해당하는 게시글 리스트와 게시글+댓글+좋아요 정보 반환")
	@GetMapping("/follow/{page}")
	public ResponseEntity<List<Post>> searchHashtagComment(@PathVariable int page) throws Exception {
		/**
		 * JWT 토큰으로 받아오기
		 */
		// Follow Hashtag를 가지고 있는 모든 게시글 리스트 가져오기
		page = Integer.max(0, page*20-1);
		
		String jwtId = jwtService.get("userid");
		
		String follow_tag = followService.searchById(jwtId).get().getHashtag();
		follow_tag = follow_tag.replace("#", "#|#");
		follow_tag = follow_tag.substring(2)+"#";
		
		List<Post> temp = postService.followHash(follow_tag);
		for(int i = 0; i<temp.size(); i++) {
			System.out.println(temp.get(i).getHashtag().toString());
		}
		
		/**
		 * 여기 바꾸기
		 */
		List<Integer> pno_by_all_hash = postService.followHashPno(follow_tag);
		List<Post> post_list = null;
		post_list = returnPost(temp, pno_by_all_hash);
		
		return new ResponseEntity<List<Post>>(post_list, HttpStatus.OK);
	}
	
	/**
	 * [공통 코드]
	 * - Comment, Likes 처리
	 * @throws UnauthorizedException 
	 */
	public List<Post> returnPost(List<Post> posts, List<Integer> pno_list) throws UnauthorizedException{
		String jwtId = jwtService.get("userid");
		
		List<Post> post_list = posts;
		List<Integer> my_like_post = likesService.selectPnoById(jwtId);
		
		// 게시글 중 내가 좋아요 눌렀는지 여부
		List<Boolean> post_boolean_list = likesService.likeBooleanPost(my_like_post, pno_list);
		
		// 내가 누른 모든 [댓글] 좋아요 리스트
		List<Integer> my_like_comment = likesService.selectCnoById(jwtId);
		
		
		for(int i = 0, post_size = post_list.size(); i<post_size; i++) {
			Post post = post_list.get(i);
			boolean check = post_boolean_list.get(i);
			
			funcPost(post, check, my_like_comment);
			
		}
		return post_list;
	}
	
	private void funcPost(Post post, boolean check, List<Integer> my_like_comment) {
		
		// Like_Count, Like_Check 설정
		if(check) {
			post.setLike_check(true);
		}
		post.setLike_count(post.getPostlike().size());
		post.setPostlike(null);
		
		/**
		 *  게시글 익명 처리
		 */
		if(post.getAnonymous() == 1) {
			//post.setId("익명");			// 나중에 개인 식별 코드 컬럼으로 대체
			post.setNickname("익명");
		}
		post.setUser(null);
		
		// 해당 게시글(pno)에 달린 댓글 리스트
		List<Comment> temp_comments_list = post.getComment();
		// 내가 좋아요 누른 댓글 리스트
		List<Boolean> like_boolean_list = likesService.likeBooleanComment(my_like_comment, post.getPno());
		
		temp_comments_list = funcComment(temp_comments_list, like_boolean_list);
	}
	
	
	/**
	 * [공통 코드] 댓글 관리
	 */
	private List<Comment> funcComment(List<Comment> temp_comments_list, List<Boolean> like_boolean_list) {
		
		if(temp_comments_list != null) {
			for(int j = 0, comm_size = temp_comments_list.size(); j<comm_size; j++) {
				Comment temp_comment = temp_comments_list.get(j);
				
				// StackOverflow 방지
				temp_comment.setLike_count(temp_comment.getLike().size());
				temp_comment.setLike(null); 
				if(like_boolean_list != null && like_boolean_list.get(j)) {	//내가 좋아요를 누른 댓글일 경우
					temp_comment.setLike_check(true);
				}
				
				/**
				 *  댓글 익명 처리
				 */
				if(temp_comment.getAnonymous() == 1) {
					//post.setId("익명");			// 나중에 개인 식별 코드 컬럼으로 대체
					temp_comment.setNickname("익명");
				}
				temp_comment.setUser(null);
				
				if(temp_comment.getParent()==0) {
					temp_comment.setParent(temp_comment.getCno());
				}
			}
		}
		Collections.sort(temp_comments_list, new Comparator<Comment>() {
			@Override
			public int compare(Comment o1, Comment o2) {
				if(o1.getParent()==o2.getParent()) {
					return o1.getCno()-o2.getCno();
				} else {
					return o1.getParent()-o2.getParent();
				}
			}
		});
		return temp_comments_list;
	}	
	
	
	/**
	 * [Me]
	 * - 내가 좋아요 누른 글 목록 가져오기
	 */
	@ApiOperation(value="내가 좋아요 누른 게시글 @번호 가져오기")
	@PostMapping("/me/{page}")
	public ResponseEntity<List<Post>> searchPostLikesById(@PathVariable int page) throws Exception {
		
		page = Integer.max(0, page*20-1);
		
		String jwtId = jwtService.get("userid");		
		List<Integer> pno_list = likesService.selectPnoById(jwtId); //좋아요 누른 글 리스트 리스트 반환
		List<Post> post_list = postService.searchAllFollowList(pno_list, page);	//개수 제한
		
		returnPost(post_list, pno_list);
		
		return new ResponseEntity<List<Post>>(post_list, HttpStatus.OK);
	}
	
	/**
	 * Post CRUD
	 */
	// DB테이블에 있는 모든 Post 조회
	@ApiOperation(value = "(DB테이블에 있는)모든 Post 목록 조회")
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> searchAll() throws Exception {
		List<Post> posts = postService.searchAll();
		for(int i = 0, size = posts.size(); i<size; i++) {
			Post post = posts.get(i);
			post.setUser(null);
			post.setPostlike(null);
			
			List<Comment> comment_list = post.getComment();
			
			for(int j = 0, jsize = comment_list.size(); j<jsize; j++) {
				Comment comment = post.getComment().get(j);
				comment.setLike(null);
				comment.setUser(null);
				
				if(comment.getParent()==0) {
					comment.setParent(comment.getCno());
				}
				
				/**
				 *  댓글 익명 처리
				 */
				if(comment.getAnonymous() == 1) {
					//post.setId("익명");			// 나중에 개인 식별 코드 컬럼으로 대체
					comment.setNickname("익명");
				}
			}
			
			Collections.sort(comment_list, new Comparator<Comment>() {
				@Override
				public int compare(Comment o1, Comment o2) {
					if(o1.getParent()==o2.getParent()) {
						return o1.getCno()-o2.getCno();
					} else {
						return o1.getParent()-o2.getParent();
					}
				}
			});
		}
		return handleSuccess(posts);
	}
	
	// Post 등록
	@ApiOperation(value = "Post 등록")
	@PostMapping
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Post post) throws Exception {
		
		System.out.println("게시물 등록 시작");
		
		String id = post.getId();
		String hashtag = post.getHashtag();
		post.setHashtag(hashtag+"#");

		// 닉네임 설정
		User user = userService.MyInfo();
		String nickname = user.getNickname();
		post.setNickname(nickname);
		
		/**
		 * post.getId()와  Id가 다를 때 비교 해야 하나?
		 */
		//관리자가 아닌데 공지사항 히든태그를 사용할 경우 BAD_REQUEST
		if((user.getAuth()==null 
				|| (user.getAuth()!=null && !user.getAuth().equals("관리자")))
				&& post.getHashtag().startsWith("__공지사항__")) {
			
			System.out.println("공지사항 권한이 없습니다.");
			return handleFail("fail", HttpStatus.BAD_REQUEST);
		} else {
			System.out.println("====== "+post.getContent()+", "+post.getHashtag());
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
		String jwtId = jwtService.get("userid");
		Post post = postService.search(pno).get();
		
		if(post.getId().equals(jwtId)) {
			postService.delete(pno);
			return handleSuccess("Post 삭제 완료");
		} else {
			return handleFail("게시글 삭제 권한이 없습니다.", HttpStatus.OK);
		}
	}
	
	// Post 수정
	@ApiOperation(value = "Post 수정")
	@PutMapping()
	public ResponseEntity<Map<String, Object>> update(@RequestBody Post post) throws Exception {
		/**
		 * 작성자만 가능
		 */
		
		String jwtId = jwtService.get("userid");
		System.out.println("ID: "+jwtId+" vs "+post.getId());
		
		if(post.getId().equals(jwtId)) {
			postService.update(post);
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
	@GetMapping("/{pno}")
	public ResponseEntity<Post> searchByPno(@PathVariable int pno) throws Exception {
		
		Post post = search(pno);
		List<Integer> pno_list = new ArrayList<Integer>();
		pno_list.add(pno);
		
		String jwtId = jwtService.get("userid");
		List<Integer> my_like_post = likesService.selectPnoById(jwtId);
		List<Boolean> like_boolean_post = likesService.likeBooleanPost(my_like_post, pno_list);
		List<Integer> my_like_comment = likesService.selectCnoById(jwtId);
		
		funcPost(post, like_boolean_post.get(0), my_like_comment);

		return new ResponseEntity<Post>(post, HttpStatus.OK);
	}

	// pno에 해당하는 Post 조회 (함수)
	@ApiOperation(value = "pno에 해당하는 Post 조회")
	public Post search(int pno) throws Exception {
		return postService.search(pno).get();
	}

	@ApiOperation(value="{pno}에 해당하는 댓글, 좋아요 조회")
	@GetMapping("/comment/{pno}")
	// pno에 해당하는 CommentList 조회
	public ResponseEntity<List<Comment>> searchComments(@PathVariable int pno) throws Exception {
		List<Comment> comments = commentService.searchPno(pno);
		
		String jwtId = jwtService.get("userid");
		
		// 내가 누른 모든 [댓글] 좋아요 리스트
		List<Integer> my_like_comment = likesService.selectCnoById(jwtId);
		// 내가 좋아요 누른 댓글 리스트
		List<Boolean> like_boolean_list = likesService.likeBooleanComment(my_like_comment, pno);
		
		funcComment(comments, like_boolean_list);
				
		return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
	}
	
	
	
	/**
	 * 베스트 게시글
	 */
	@ApiOperation(value="[Best20] 최근 한 달 간 좋아요가 가장 높은 상위 20개 게시글 전부 가져오기")
	@GetMapping("/best")
	public ResponseEntity<List<Post>> bestPost() throws Exception {
		// 해시태그에 해당하는 게시글 리스트 가져오기
		List<Post> post_list = postService.searchAMonth();
		List<Integer> pno_list = postService.searchPnoAMonth();
		
		System.out.println("Best 게시글 : " + post_list.size() + ", " + pno_list.size());
		
		for(int i = 0, size = post_list.size(); i<size; i++) {
			Post post = post_list.get(i);
			post.setLike_count(post.getPostlike().size());
		}
		post_list = returnPost(post_list, pno_list);
		
		// 리스트 정렬
		Collections.sort(post_list, new Comparator<Post>() {
			@Override
			public int compare(Post o1, Post o2) {
				return o1.getLike_count()-o2.getLike_count();
			}
		});
		
		post_list = post_list.subList(0, Integer.min(20, post_list.size()));
		
		return new ResponseEntity<List<Post>>(post_list, HttpStatus.OK);
	}
		
	// 해시태그에 해당하는 Post 리스트 조회 (함수)
	public List<Post> searchPostByHash(String hashtag, int page) throws Exception {
		List<Post> posts = postService.search(hashtag, page);
		return posts;
	}
	
		
	/**
	 * Comment
	 */
	// Comment 작성
	@ApiOperation(value="Comment 작성")
	@PostMapping("/comment")
	public ResponseEntity<Map<String, Object>> commentInsert(@RequestBody Comment comment) throws Exception {
		String jwtId = jwtService.get("userid");
		
		commentService.insert(jwtId, comment);
		
		String id = null;
		
		// 댓글 알림 처리
		Notification notification = null;
		if(comment.getParent() == 0) { //댓글일 경우 - 게시글 작성자에게 전송
			id = postService.search(comment.getPno()).get().getId();
			notification =  new Notification(id, new Date(), comment.getPno(), 2, 0, comment.getContent());
		} else { //대댓글일 경우 - 댓글 작성자에게 전송
			id = commentService.search(comment.getCno()).getId();
			notification =  new Notification(id, new Date(), comment.getPno(), 4, 0, comment.getContent());
		}
		
		notificationService.insert(notification);
		
		return handleSuccess("댓글 등록 완료");
	}
	
	// Comment 수정
	@ApiOperation(value="Comment update")
	@PutMapping("/comment")
	public ResponseEntity<Map<String, Object>> commentUpdate(@RequestBody Comment comment) throws Exception {
		/**
		 * 작성자만 가능
		 */
		String jwtId = jwtService.get("userid");
		
		if(comment.getId() == jwtId) {
			commentService.update(comment);
			return handleSuccess("댓글 수정 완료");
		} else {
			return handleSuccess("댓글 삭제 권한이 없습니다.");
		}
	}
	
	// Comment 삭제
	@ApiOperation(value="no로 Comments 삭제")
	@DeleteMapping("/comment/{no}")
	public ResponseEntity<Map<String, Object>> DeleteComment(@PathVariable int no) throws Exception {
		/**
		 * 작성자만 가능
		 */
		String jwtId = jwtService.get("userid");
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
