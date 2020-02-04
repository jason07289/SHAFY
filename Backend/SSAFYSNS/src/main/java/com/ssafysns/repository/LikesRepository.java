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

	@Modifying
	@Query("delete from likes l where l.id=:id and l.pno=:pno and l.cno=:cno")
	void deleteLikes(@Param("id") String id, @Param("pno") int pno, @Param("cno") int cno);

	// 내가 누른 모든 게시글 + 댓글의 좋아요를 게시글 번호 리스트(pno)로 가져오기
	@Query("select l.pno from likes l where l.id=:id")
	List<Integer> searchPnoById(@Param("id") String id);
	
	// 내가 누른 모든 게시글 + 댓글의 좋아요를 댓글 번호 리스트(cno)로 가져오기
	@Query("select l.cno from likes l where l.id=:id")
	List<Integer> searchCnoById(@Param("id") String id);

	// pno게시글의 댓글들이 내가 좋아요 누른건지 안누른건지 판별해서 boolean 리스트로 반환
	@Query(value = "select case when c.cno in :cno_list then 'true' else 'false' end from comment c where c.pno = :pno", nativeQuery = true)
	List<Boolean> checkLikeTest(@Param("cno_list") List<Integer> cno_list, @Param("pno") int pno);
}
