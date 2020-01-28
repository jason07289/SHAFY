package com.ssafysns.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.BookmarkHashtag;

@Repository
public interface BookmarkHashtagRepository extends JpaRepository<BookmarkHashtag, Integer> {
//	Optional<BookmarkHashtag> findById(String id);

	@Query(value = "select b from bookmark_hashtag b where b.id = :id")
	List<BookmarkHashtag> findByUserId(@Param("id") String id);
}
