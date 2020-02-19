package com.ssafysns.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafysns.model.dto.Vote;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

	@Query("select v.vno from vote v where id=:id and v.chk=0 order by vno desc")
	public int searchByUserId(@Param("id") String id);

	@Query("select v from vote v where pno=:pno")
	public Vote findByPno(@Param("pno") int pno);

}
 