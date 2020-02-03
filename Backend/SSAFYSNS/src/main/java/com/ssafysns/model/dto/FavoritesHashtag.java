package com.ssafysns.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Setter
@Getter
@Entity(name="favorites_hashtag")
public class FavoritesHashtag {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false,unique=true)
	private int no;
	
	@Column(length=50)
	private String hashtag;
	
	@Column(length=30,nullable=false)
	private String id;
	
//	@Column(nullable=false) 
//	private boolean deleted=false;
	
	// 외래키 설정
	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_favorites_hashtag_id"))
	private User user;
}
