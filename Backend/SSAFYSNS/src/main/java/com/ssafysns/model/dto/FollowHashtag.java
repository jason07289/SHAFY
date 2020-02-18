package com.ssafysns.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Setter
@Getter
@Entity(name = "follow_hashtag")
public class FollowHashtag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private int no;

	// 2020.02.10 hashtag는 id 하나당 하나의 String으로 들어옴 ex)"#SSAFY#2기#삼성" 띄어쓰기 없음!
	@Column(length = 1000)
	private String hashtag;

	// 2020.02.10 id를 unique로 수정함!
	@Column(length = 30, nullable = false, unique = true)
	private String id;

//	@Column(nullable = false)
//	private boolean deleted;

	// 2020.02.10 @ManyToOne을 @OneToOne으로 수정함!
	// 외래키 설정 (id는 User의 id를 참조함)
	@OneToOne
	@JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_followhashtag_id"))
	private User user;
}
