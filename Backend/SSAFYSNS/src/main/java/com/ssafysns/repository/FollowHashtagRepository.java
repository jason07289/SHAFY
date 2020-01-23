package com.ssafysns.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.FollowHashtag;

@Repository
public interface FollowHashtagRepository extends JpaRepository<FollowHashtag, Integer> {
	@Query(value = "select f from follow_hashtag f where f.id = :id")
	List<FollowHashtag> findByUserId(@Param("id") String id);
}
