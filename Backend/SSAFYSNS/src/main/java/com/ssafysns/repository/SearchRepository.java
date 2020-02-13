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
//	@Modifying
//	@Transactional
//	@Query(value = "delete from search s where s.datetime <= :datetime")
//	void deleteBySearchtime(@Param("datetime") Date datetime);

	// datetime이 현재 날짜 기준 일주일 이내인 것만 조회) -> 최근 7주일간 인기 검색어 조회
	@Query(value = "select s.hashtag, count(distinct s.id) as cnt, ANY_VALUE(s.id) as id, ANY_VALUE(s.no) as no, ANY_VALUE(s.datetime) as datetime from search s where s.datetime >= date_add(now(), interval -7 day) group by s.hashtag order by cnt desc", nativeQuery = true)
	List<Search> findBySearchtime();

	@Query(value = "select count(distinct s.id) as cnt from search s where s.datetime >= date_add(now(), interval -7 day) and s.hashtag=:hashtag", nativeQuery = true)
	long findCntBySearchtime(@Param("hashtag") String hashtag);
}
