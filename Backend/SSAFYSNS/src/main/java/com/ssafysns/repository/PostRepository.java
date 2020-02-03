package com.ssafysns.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
	
	@Modifying
	@Query("update Post p set p.deleted = 1 where p.pno=:pno")
	void updateDeleted(@Param("pno") int pno);

	@Query("select p from Post p where p.hashtag like concat('%', :hashtag, '%')")
	List<Post> findByHashtag(@Param("hashtag") String hashtag);
	
	@Query("select p from Post p where p.pno in :pno_list")
	List<Post> findByPnoList(@Param("pno_list") List<Integer> pno_list);
	
	@Query("select p.pno from Post p where p.hashtag like concat('%', :hashtag, '%')")
	List<Integer> findPnoByHashtag(String hashtag);	
	
//	@Query("select c from comment c where c.pno in list")
//	List<Comment> testNewEntity(@Param("list") List<Integer> hashtag_list);
	
	@Query("select distinct(p.pno) from follow_hashtag f, Post p where p.hashtag like concat('%', f.hashtag, '%') and (f.id=:id)")
	List<Integer> followHashPno(@Param("id") String id);

}
