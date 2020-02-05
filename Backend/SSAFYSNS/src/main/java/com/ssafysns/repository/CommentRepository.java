package com.ssafysns.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select c from comment c where c.user.id=:id")
	List<Comment> findById(String id);

	@Modifying
	@Query("update comment c set c.deleted = 1 where c.cno=:cno")
	void updateDeleted(@Param("cno") int cno); 

	@Query("select c from comment c where c.post.pno = :pno")
	List<Comment> findByPno(@Param("pno") int pno);
	
	@Query("select c from comment c, Post p where c.post.pno = p.pno and p.hashtag like concat('%', :hashtag, '%')")
	List<Comment> joinCustom(@Param("hashtag") String hashtag);
	
//	@Query("select c from comment c, (select p from Post p where p.hashtag like concat('%', :hashtag, '%')) as post where c.pno = post.pno")
//	List<Comment> joinCustomComment(@Param("hashtag") String hashtag);
	
	@Query("select c from comment c where c.post.pno in (select post.pno from Post post where post.hashtag like concat('%', :hashtag, '%'))")
	List<Comment> joinCustomComment(@Param("hashtag") String hashtag);	
	
	@Query("select c from comment c where c.post.pno in :plist")
	List<Comment> customAllPno(@Param("plist") List<Integer> plist);

	// 얘는 어따쓰는거?????
	@Query("select c from comment c where c.post.pno = :pno and c.cno = :cno")
	Comment searchByPnoCno(@Param("pno") int pno, @Param("cno") int cno);

	/*
	@Modifying
	@Query("update comment c set like_count = like_count where c.cno = :cno")// c.like_count = c.like_count+1 where c.cno = :cno")
	void likesUpComment(@Param("cno") int cno);
	
	@Modifying
	@Query("update comment c set c.pno = c.pno where c.cno = :cno")// c.like_count = c.like_count-1 where c.cno = :cno")
	void likesDownComment(@Param("cno") int cno);
	*/

	@Query("select c.cno from comment c where c.post.pno = :pno")
	List<Integer> searchCnoByPno(@Param("pno") int pno);


}
