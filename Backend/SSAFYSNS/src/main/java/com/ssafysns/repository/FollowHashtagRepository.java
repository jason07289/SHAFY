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

@Repository
public interface FollowHashtagRepository extends JpaRepository<FollowHashtag, Integer> {

	// delete, update 메서드 위에 @Modifying, @Transactional annotation 필수!
	@Modifying
	@Transactional
	@Query(value = "delete from follow_hashtag f where f.id = :id")
	void deleteByUserId(@Param("id") String id);

	@Modifying
	@Transactional
	@Query(value = "update follow_hashtag f set f.hashtag=:hashtag where f.id = :id")
	void updateByUserId(@Param("id") String id, @Param("hashtag") String hashtag);

//	@Modifying
//	@Transactional
//	@Query(value = "delete from follow_hashtag t where t.id = :id and t.hashtag = :hashtag")
//	void deleteByUserIdAndHashtag(@Param("id") String id, @Param("hashtag") String hashtag);

//	@Query(value = "select f from follow_hashtag f where f.id = :id and f.hashtag = :hashtag")
//	Optional<FollowHashtag> findByUserIdAndHashtag(@Param("id") String id, @Param("hashtag") String hashtag);

	@Query(value = "select f from follow_hashtag f where f.id = :id")
	Optional<FollowHashtag> findByUserId(@Param("id") String id);
}
