package com.ssafysns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
	@Modifying
	@Query("update Post p set p.deleted = 1 where p.pno=:pno")
	void updateDeleted(@Param("pno") int pno);
}
