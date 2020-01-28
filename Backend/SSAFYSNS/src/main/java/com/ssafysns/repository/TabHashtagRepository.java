package com.ssafysns.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.TabHashtag;

@Repository
public interface TabHashtagRepository extends JpaRepository<TabHashtag, Integer> {
//	Optional<TabHashtag> findById(String id);

	@Query(value = "select t from tab_hashtag t where t.id = :id")
	List<TabHashtag> findByUserId(@Param("id") String id);
}