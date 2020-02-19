package com.ssafysns.repository;

import java.util.List;

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
	VoteRecord isVoted(@Param("id") String id, @Param("vno") int vno);

	@Query("select vr.vno from vote_record vr where vr.id=:id")
	List<Integer> searchById(String id);

	@Query(value="select count(vr.a_value) from vote_record vr where vr.vno=:vno", nativeQuery=true)
	int getAValue(int vno);

	@Query(value="select count(vr.b_value) from vote_record vr where vr.vno=:vno", nativeQuery=true)
	int getBValue(int vno);
}
