package com.ssafysns.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ssafysns.model.dto.Likes;
import com.ssafysns.model.dto.Vote;
import com.ssafysns.model.dto.VoteRecord;

public interface VoteRecordRepository extends JpaRepository<VoteRecord, Integer> {

	@Modifying
	@Query("delete from vote_record vr where vr.id=:id and vr.vno=:vno") 
	void deleteVotes(@Param("vno") int vno, @Param("id") String id);

	@Query("select vr from vote_record vr where vr.id=:id and vr.vno=:vno")
	Likes checkLikes(@Param("id") String id, @Param("vno") int vno);

	@Query(value = "select vr from vote_record vr where vr.id=:id and vr.vno=:vno")
	Vote isVoted(@Param("id") String id, @Param("vno") int vno);
}
