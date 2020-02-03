package com.ssafysns.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafysns.model.dto.Bookmark;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {

	// delete, update 메서드 위에 @Modifying, @Transactional annotation 필수!
	@Modifying
	@Transactional
	@Query(value = "delete from bookmark b where b.id = :id and b.pno = :pno")
	void deleteByUserIdAndPno(@Param("id") String id, @Param("pno") int pno);

	@Modifying
	@Transactional
	@Query(value = "delete from bookmark b where b.id = :id")
	void deleteByUserId(@Param("id") String id);

	@Query(value = "select b from bookmark b where b.id = :id and b.pno = :pno")
	Optional<Bookmark> findByUserIdAndPno(@Param("id") String id, @Param("pno") int pno);

	@Query(value = "select b from bookmark b where b.id = :id")
	List<Bookmark> findByUserId(@Param("id") String id);
}