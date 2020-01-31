package com.ssafysns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {
	@Modifying
	@Query("update Notice n set n.deleted = 1 where n.no=:no") // 1: true, 0: false
	void updateDeleted(@Param("no") int no);
}
