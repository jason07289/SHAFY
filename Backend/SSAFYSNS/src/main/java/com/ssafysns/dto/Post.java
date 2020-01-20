package com.ssafysns.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString // toString method 생성 ->롬복
//@Data// @Data 어노테이션으로 게터 세터를 알아서 처리 -> 롬복
@Entity // db의 테이블과 일대일로 매칭되는 객체 단위 @Id 어노테이션으로 pk 지정
/*
 * application.properties의 "spring.jpa.hibernate.ddl-auto=update" 때문에
 * entityManager가 자동으로 DDL을 수행해준다. 콘솔에서 확인가능.
 *
 * @Entity(name="user")로도 table명 지정가능 CamelCase가 java에서 쓰이고 db는 under_score기 때문에
 * 명시해주는게 좋음
 */
@Table(name = "post") // name을 통해 테이블 명을 지정가능, 이 어노테이션이 없다면 class명을 default로 table생성
public class Post {
	@Id // 기본키 (pk)
	@GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY로 해야 Auto Increment
	@Column(nullable = false, unique = true)
	private int pno;

//	@NotNull 이걸로도 낫 널 지정 가능 이렇게 하면 자바상에서도 에러를 잡아줘서 더 좋긴함
	@Column(length = 30, nullable = false)
	private String title;

	@Column(length = 1000, nullable = false)
	private String content;

	@Column(length = 500)
	private String hashtag;

	@Column(length = 30)
	private String id;

	@Column(length = 20, nullable = false)
	private String nickname;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;

	@Column()
	private int views;

	@Column()
	private int likes;

	@Column(length = 200)
	private String attachments;

	@Column()
	private boolean deleted;
	
	// 외래키 설정
	@OneToMany(mappedBy="post")
//	@JoinColumn(name="no")
    private List<Notification> notifications = new ArrayList<Notification>();
}
