package com.ssafysns.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Setter
@Getter
@Entity(name = "tab_hashtag")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class TabHashtag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private int no;

	// 2020.02.10 hashtag는 id 하나당 하나의 String으로 들어옴 ex)"#SSAFY#2기#삼성" 띄어쓰기 없음!
	@Column(length = 500)
	private String hashtag;

	// 2020.02.10 id를 unique로 수정함!
	@Column(length = 30, nullable = false, unique = true)
	private String id;
	
	public TabHashtag(String hashtag, String id) {
		this.hashtag = hashtag;
		this.id = id;
	}

	// 2020.02.10 @ManyToOne을 @OneToOne으로 수정함!
	// 외래키 설정 (id는 User의 id를 참조함)
	@OneToOne
	@JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_tab_hashtag_id"))
	private User user;
}
