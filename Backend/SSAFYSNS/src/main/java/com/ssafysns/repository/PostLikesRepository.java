package com.ssafysns.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.PostLikes;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Integer>{

	@Modifying
	@Query("delete from post_likes p where p.user.id=:id and p.post.pno=:pno")
	void deleteLikes(@Param("id") String id, @Param("pno") int pno); 

//	// pno게시글의 댓글들이 내가 좋아요 누른건지 안누른건지 판별해서 boolean 리스트로 반환
//	@Query(value = "select case when c.cno in :cno_list then 'true' else 'false' end from comment c where c.pno = :pno", nativeQuery = true)
//	List<Boolean> checkLikeTest(@Param("cno_list") List<Integer> cno_list, @Param("pno") int pno);
}
