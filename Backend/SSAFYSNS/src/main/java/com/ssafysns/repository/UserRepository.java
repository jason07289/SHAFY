package com.ssafysns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafysns.model.dto.User;
@Repository
public interface UserRepository extends JpaRepository<User, String>{//<entity, pk의 타입>
	
	
	public User findByIdAndName(String id, String name );
	
	
	
/*	 
 * ......................... 
 * jpaRepository 상속시 즉시 쓸 수 있는 Query 메소드
 * 
 *  save() -> insert, update
 * 	getOne(String id) -> pk로 한 건 찾기     
 *  findAll() -> 전체 레코드 불러오기, 정렬, 페이징 가능
 *  count() -> 레코드 개수
 *  delete() -> 레코드 삭제    
 *  .................... 
 *  내가 추가할 수 있는 Query 메소드
 *  
 *  findBy컬럼명
 *  countBy컬럼명
 *  ex) 
 *  Page<User> findByName(String name, Pageable pageable);
 *  
 *  findByEmailAndUserId(String email, String userId)
 *  findByEmailOrUserId(String email, String userId)
 *  findByCreatedAtBetween(Date fromDate, Date toDate)
 *  findByAgeLessThanEqual(int age)
 *  findByAgeGraterThanEqual(int age)
 *  
 *  findByNameLike(String name)  // %name% 이런거인듯
 *  Name은 컬럼명
 *  findByJobIsNull()
 *  Job은 컬럼명
 *  
 *  findByEmailOrderByNameAsc(String email)
 *  Desc도 될듯...
 */
}
