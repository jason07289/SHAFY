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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.FAQ;
import com.ssafysns.model.dto.FollowHashtag;
import com.ssafysns.model.dto.Likes;
import com.ssafysns.model.dto.Post;
import com.ssafysns.model.dto.User;
import com.ssafysns.model.service.CommentService;
import com.ssafysns.model.service.FollowHashtagService;
import com.ssafysns.model.service.JwtService;
import com.ssafysns.model.service.LikesService;
import com.ssafysns.model.service.PostService;
import com.ssafysns.model.service.UserService;
import com.ssafysns.repository.CommentRepository;
import com.ssafysns.repository.PostRepository;

import io.swagger.annotations.ApiOperation;
import javassist.compiler.ast.Pair;

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
	JwtService jwtService;
	
	String jwtId = "kimssafy";
	
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
	 * Pno 하나에 해당하는 Post, List<Comment> 조회
	 */
	@ApiOperation(value="{pno}에 해당하는 게시글, 댓글, 좋아요 조회")
	@GetMapping("/post/{pno}")
	public ResponseEntity<Map<String, Object>> searchByPno(@PathVariable int pno) throws Exception {
		Map<String, Object> pnoMap = new HashMap<String, Object>();
		
		Post post = search(pno);
		
		//return
		if(post != null) {
			List<Comment> comments = searchComments(pno);
			
			for(int i = 0, size = comments.size(); i<size; i++) {
				comments.get(i).setPost(null); //여기야
				comments.get(i).setLike(null);
				comments.get(i).setUser(null);
				if(comments.get(i).getAnonymous() == 1) {	//익명일 경우
					comments.get(i).setId(null);
				}
			}
			List<Likes> likes = searchLikes(pno);
			pnoMap.put("post", post);
			pnoMap.put("comment", comments);
			pnoMap.put("likes", likes);
		} else {
			pnoMap.put("errMsg", false);
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
	
	// pno에 해당하는 LikeList 조회 (함수)
	private List<Likes> searchLikes(int pno) {
		List<Likes> likes = likesService.searchByPno(pno);
		return likes;
	}
	
	
	/**
	 * [탭]
	 * - 한 개의 hashtag에 해당하는 모든 Post, Comment, Likes 조회
	 */
	@ApiOperation(value="하나의 hashtag에 해당하는 Post & Comment & Likes 목록 조회")
	@GetMapping("/{hashtag}/one")
	public ResponseEntity<Map<String, Object>> searchByHashtag(@PathVariable String hashtag) throws Exception {
		List<Post> posts = searchPostByHash(hashtag);
		List<Comment> comments = searchCommentByHash(hashtag);
		List<Integer> pnos = postService.searchPnoList(hashtag);
		List<Likes> likes = likesService.searchByAllPno(pnos);
		
		Map<String, Object> aHashMap = new HashMap<String, Object>();
		aHashMap.put("post", posts);
		aHashMap.put("comment", comments);
		aHashMap.put("like", likes);
		
		return handleSuccess(aHashMap);
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
	 * [뉴스피드]
	 * - 여러개의 Hashtag에 해당하는 Post와 그에 해당하는 Comment, Likes 조회
	 * @return Map
	 */
	@ApiOperation(value="여러개의 Hashtag에 해당하는 Post와 Comment")
	@GetMapping("/hashtag/all")
	public ResponseEntity<Map<String, Object>> searchHashtagComment() throws Exception {
		/**
		 * JWT 토큰으로 받아오기
		 */
//		Map<String, Object> jwt = jwtService.get("userid");
//		String jwtId = jwt.get("userid").toString();
		
		List<Integer> pno_list_by_all_hash = postService.followHashPno(jwtId);
		List<Post> posts_by_all_hash = postService.searchAllFollowList(pno_list_by_all_hash);
		List<Comment> comments_by_all_hash = commentService.searchAllCommenetList(pno_list_by_all_hash);
		List<Likes> likes_by_all_hash = likesService.searchByAllPno(pno_list_by_all_hash);
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		//return
		result.put("post", posts_by_all_hash);
		result.put("comment", comments_by_all_hash);
		result.put("like", likes_by_all_hash);
		
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
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
