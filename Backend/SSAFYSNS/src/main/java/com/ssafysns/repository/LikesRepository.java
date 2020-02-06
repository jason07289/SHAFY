package com.ssafysns.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.Comment;
import com.ssafysns.model.dto.Likes;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer>{

	@Query("select l from likes l where l.id=:id and l.cno=:cno")
	Likes checkLikes(@Param("id") String id, @Param("cno") int cno);
	
	// 댓글 좋아요 취소
	@Modifying
	@Query("delete from likes l where l.id=:id and l.pno=:pno and l.cno=:cno")
	void deleteLikes(@Param("id") String id, @Param("pno") int pno, @Param("cno") int cno);

	// 내가 누른 모든 댓글의 좋아요를 댓글 번호 리스트(cno)로 가져오기
	@Query("select l.cno from likes l where l.id=:id")
	List<Integer> searchCnoById(@Param("id") String id);

	// pno게시글의 댓글들이 내가 좋아요 누른건지 안누른건지 판별해서 boolean 리스트로 반환
	@Query(value = "select case when c.cno in :cno_list then 'true' else 'false' end from comment c where c.pno = :pno", nativeQuery = true)
	List<Boolean> checkLikeComment(@Param("cno_list") List<Integer> cno_list, @Param("pno") int pno);

	@Query(value = "select case when p.pno in :my_like_post then 'true' else 'false' end from Post p where p.pno in :follow_list")
	List<Boolean> checkLikePost(@Param("my_like_post") List<Integer> my_like_post, @Param("follow_list") List<Integer> follow_list);

}
