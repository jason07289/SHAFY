 package com.ssafysns.repository;

import java.awt.print.Pageable;
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
	
	
	//최근 한 달 내 게시글 가져오기
	@Query(value="select * from post p where p.datetime between date_add(now(), interval -1 month) and now() and p.deleted=0 order by p.pno desc", nativeQuery = true)
	List<Post> findAMonth();
	
	@Query(value="select p.pno from post p where p.datetime between date_add(now(), interval -1 month) and now() and p.deleted=0", nativeQuery = true)
	List<Integer> findPnoAMonth();
	
	@Modifying
	@Query("update Post p set p.deleted = 1 where p.pno=:pno")
	void updateDeleted(@Param("pno") int pno);

	@Query(value = "select * from post p where concat(p.hashtag, '#') like concat('%', :hashtag, '%') and p.deleted=0 order by p.pno desc limit :page, :limit", nativeQuery=true)
	List<Post> findByHashtag(@Param("hashtag") String hashtag, @Param("page") int page, @Param("limit") int limit);
	
	@Query(value = "select * from post p where concat(p.hashtag, '#') like concat('%', :hashtag, '%') and p.deleted=0 order by p.pno desc limit :page, 1", nativeQuery=true)
	Post isLastPage(@Param("hashtag") String hashtag, @Param("page") int page);
	
	@Query(value = "select * from post p where p.pno in :pno_list order by p.pno desc limit :page, 1", nativeQuery=true)
	Post isLastPage(@Param("pno_list") List<Integer> pno_list, @Param("page") int page);
	
	@Query(value = "select * from post p where p.pno in :pno_list and p.deleted=0 order by p.pno desc limit :page, :limit", nativeQuery=true)
	List<Post> findByPnoList(@Param("pno_list") List<Integer> pno_list, @Param("page") int page, @Param("limit") int limit);
	
	@Query(value = "select p.pno from post p where concat(p.hashtag, '#') like concat('%', :hashtag, '%') and p.deleted=0", nativeQuery=true)
	List<Integer> findPnoByHashtag(String hashtag);	
	
//	@Query("select distinct(p.pno) from follow_hashtag f, Post p where p.hashtag like concat('%', f.hashtag, '%') and (f.id=:id) and p.deleted=0")
//	List<Integer> followHashPnoById(@Param("id") String id);

	@Query(value = "select p.pno from post p where concat(p.hashtag, '#') regexp :hashtag", nativeQuery = true)
	List<Integer> followHashPno(@Param("hashtag") String hashtag);
	
	@Query("select p from Post p where p.pno=:pno and p.deleted=0")
	Post findByPno(@Param("pno") int pno);
	
	@Query(value = "select p.pno from post p where concat(p.hashtag, '#') regexp :hashtag", nativeQuery=true) 
	List<Integer> followHash(@Param("hashtag") String hashtag);

	// 내가 쓴 글 pno_list
	@Query(value="select * from post p where p.id=:jwtId", nativeQuery=true)
	List<Integer> searchMyPost(@Param("jwtId") String jwtId);

	@Query(value="select * from post p where p.id=:id order by datetime desc limit :top", nativeQuery=true)
	Post checkLastVotePost(@Param("id") String id, @Param("top") int top);

}
