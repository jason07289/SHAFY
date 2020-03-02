package com.ssafysns.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafysns.model.dto.User;

@Repository
public interface AdminRepository extends JpaRepository<User, Integer> {

	// delete, update 메서드 위에 @Modifying, @Transactional annotation 필수!
	@Modifying
	@Transactional
	@Query(value = "update user u set u.auth=:auth where u.id=:id", nativeQuery=true)
	void updateAuth(@Param("id") String id, @Param("auth") String auth);

	@Modifying
	@Transactional
	@Query(value = "update user u set u.banned=:banned where u.id=:id", nativeQuery=true)
	void updateBanned(@Param("id") String id, @Param("banned") int banned);
}