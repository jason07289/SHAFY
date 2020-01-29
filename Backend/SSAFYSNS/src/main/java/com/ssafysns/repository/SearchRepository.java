package com.ssafysns.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ssafysns.model.dto.Search;

public interface SearchRepository extends JpaRepository<Search, Integer> {
	@Modifying
	@Transactional
	@Query(value = "delete from search s where s.datetime <= :datetime")
	void deleteBySearchtime(@Param("datetime") Date datetime);

//	@Query(value = "select s from search s where datetime <= now()")
	@Query(value = "select s.hashtag, count(distinct s.id) as cnt, s.id, s.no from search s where s.datetime >= date_add(now(), interval -7 day) group by s.hashtag order by s.cnt desc")
	List<Search> findBySearchtime();
}
