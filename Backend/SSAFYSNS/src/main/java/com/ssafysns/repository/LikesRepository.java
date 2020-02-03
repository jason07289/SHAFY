package com.ssafysns.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.Likes;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer>{

	@Modifying
	@Query("delete from likes l where l.id=:id and l.pno=:pno and l.cno=:cno")
	void deleteLikes(@Param("id") String id, @Param("pno") int pno, @Param("cno") int cno);

	@Query("select l.pno from likes l where l.id=:id")
	List<Integer> searchById(@Param("id") String id);

	@Query("select l from likes l where l.pno in :pno")
	List<Likes> findByAllPno(@Param("pno") List<Integer> pno);


}