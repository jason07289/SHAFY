package com.ssafysns.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.FAQ;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Integer> {

	List<FAQ> findById(String id);
	
	@Modifying
	@Query("update FAQ f set f.deleted = true where f.no=:no")
	void updateDeleted(@Param("no") int no);

}
