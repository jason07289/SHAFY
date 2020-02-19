package com.ssafysns.repository;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafysns.model.dto.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	// 1. checked = true이고
	// 2. datetime 이전이면(단, datetime을 일정시점(일단, 7일로 설정함) 이전으로 설정해야만 삭제할 수 있도록 함) 삭제
	@Modifying
	@Transactional
	@Query(value = "delete from notification n where n.datetime <= date_add(now(), interval -1 week) and n.checked = 1 and n.datetime <= :datetime", nativeQuery = true)
	void deleteByDatetime(@Param("datetime") Date datetime);

	// no로 notification 조회
	@Query(value = "select n from Notification n where n.no = :no")
	Notification findByNo(@Param("no") int no);

	// 현재 로그인한 유저의 확인하지 않은 알람 개수를 리턴
	@Query(value = "select count(n) from Notification n where n.id = :id and n.checked = 0")
	long countByIdAndNotChecked(@Param("id") String id);

	@Modifying
	@Transactional
	@Query(value = "update notification set checked = 1 where no = :no", nativeQuery = true)
	void updateByChecked(@Param("no") int no);

	@Query(value = "select u.alarm from User u where u.id = :id")
	int selectUserAlarm(@Param("id") String id);

	@Query(value = "select n from Notification n where n.id = :id order by n.datetime desc limit 10")
	List<Notification> findAllByUserId(@Param("id") String id);
}
