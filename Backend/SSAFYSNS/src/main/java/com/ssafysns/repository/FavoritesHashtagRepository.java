package com.ssafysns.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafysns.model.dto.FavoritesHashtag;

@Repository
public interface FavoritesHashtagRepository extends JpaRepository<FavoritesHashtag, Integer> {
//	Optional<FavoritesHashtag> findById(String id);

	// delete, update 메서드 위에 @Modifying, @Transactional annotation 필수!
	@Modifying
	@Transactional
	@Query(value = "delete from favorites_hashtag t where t.id = :id")
	void deleteByUserId(@Param("id") String id);

	@Modifying
	@Transactional
	@Query(value = "delete from favorites_hashtag t where t.id = :id and t.hashtag = :hashtag")
	void deleteByUserIdAndHashtag(@Param("id") String id, @Param("hashtag") String hashtag);

	@Query(value = "select b from favorites_hashtag b where b.id = :id and b.hashtag = :hashtag")
	Optional<FavoritesHashtag> findByUserIdAndHashtag(@Param("id") String id, @Param("hashtag") String hashtag);

	@Query(value = "select b from favorites_hashtag b where b.id = :id")
	List<FavoritesHashtag> findByUserId(@Param("id") String id);
}
