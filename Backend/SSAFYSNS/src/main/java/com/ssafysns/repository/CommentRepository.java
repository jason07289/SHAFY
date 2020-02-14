package com.ssafysns.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select c from comment c where c.id=:id and c.deleted=0")
	List<Comment> findById(String id);

	@Modifying
	@Query("update comment c set c.deleted = 1 where c.cno=:cno and c.deleted=0")
	void updateDeleted(@Param("cno") int cno); 

	@Query("select c from comment c where c.pno=:pno and c.deleted=0")
	List<Comment> findByPno(@Param("pno") int pno);
	
	@Query("select c from comment c, Post p where c.pno = p.pno and p.hashtag like concat('%', :hashtag, '%') and c.deleted=0")
	List<Comment> joinCustom(@Param("hashtag") String hashtag);
	
//	@Query("select c from comment c, (select p from Post p where p.hashtag like concat('%', :hashtag, '%')) as post where c.pno = post.pno")
//	List<Comment> joinCustomComment(@Param("hashtag") String hashtag);
	
	@Query("select c from comment c where c.pno in (select post.pno from Post post where post.hashtag like concat('%', :hashtag, '%')) and c.deleted=0")
	List<Comment> joinCustomComment(@Param("hashtag") String hashtag);	
	
	@Query("select c from comment c where c.pno in :plist and c.deleted=0")
	List<Comment> customAllPno(@Param("plist") List<Integer> plist);

	// 얘는 어따쓰는거?????
	@Query("select c from comment c where c.pno = :pno and c.cno=:cno and c.deleted=0")
	Comment searchByPnoCno(@Param("pno") int pno, @Param("cno") int cno);

	/*
	@Modifying
	@Query("update comment c set like_count = like_count where c.cno = :cno")// c.like_count = c.like_count+1 where c.cno = :cno")
	void likesUpComment(@Param("cno") int cno);
	
	@Modifying
	@Query("update comment c set c.pno = c.pno where c.cno = :cno")// c.like_count = c.like_count-1 where c.cno = :cno")
	void likesDownComment(@Param("cno") int cno);
	*/

	@Query("select c.cno from comment c where c.pno=:pno and c.deleted=0")
	List<Integer> searchCnoByPno(@Param("pno") int pno);

	@Query("select c from comment c where c.pno=:pno and c.cno=:cno and c.deleted=0")
	Comment checkComment(@Param("pno") int pno, @Param("cno") Integer cno);

	@Query("select c from comment c where c.cno=:cno")
	Comment findByCno(@Param("cno") int cno);

	@Query("select c.pno from comment c where c.id=:id")
	List<Integer> searchMyComment(@Param("id") String id);


}
