package com.ssafysns.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.Comments;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Integer> {
	
//	@Modifying
//	@Query("update comments c set c.title = :title where c.id=:id")
//	void update(@Param("title") String title, @Param("id") String id);

	List<Comments> findById(String id);

	@Modifying
	@Query("update comments c set c.deleted = true where c.no=:no")
	void updateDeleted(@Param("no") int no);

}
