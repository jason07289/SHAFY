package com.ssafysns.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafysns.model.dto.FollowHashtag;
import com.ssafysns.model.dto.TabHashtag;

@Repository
public interface TabHashtagRepository extends JpaRepository<TabHashtag, Integer> {

	// delete, update 메서드 위에 @Modifying, @Transactional annotation 필수!
	@Modifying
	@Transactional
	@Query(value = "delete from tab_hashtag t where t.id = :id")
	void deleteByUserId(@Param("id") String id);

//	@Modifying
//	@Transactional
//	@Query(value = "delete from tab_hashtag t where t.id = :id and t.hashtag = :hashtag")
//	void deleteByUserIdAndHashtag(@Param("id") String id, @Param("hashtag") String hashtag);

//	@Query(value = "select t from tab_hashtag t where t.id = :id and t.hashtag = :hashtag")
//	Optional<TabHashtag> findByUserIdAndHashtag(@Param("id") String id, @Param("hashtag") String hashtag);

	@Query(value = "select t from tab_hashtag t where t.id = :id")
	TabHashtag findByUserId(@Param("id") String id);
}