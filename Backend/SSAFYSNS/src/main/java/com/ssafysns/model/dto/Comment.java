package com.ssafysns.model.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
@ToString
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
@Entity(name = "comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY로 해야 Auto Increment
	@Column(nullable = false, unique = true)
	private int cno;
//
	@Column(nullable = false)
	private int pno; 

	@Column()
	private int parent; 

	@Column(nullable = false, length = 30)
	private String id;
	
	@Column(length = 20)	//게시글 등록 시 직접 입력
	private String nickname;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;

	@Column(nullable = false, length = 300)
	private String content;

	@Column(columnDefinition="int default 0")
	private int deleted;
	
	@Column(columnDefinition = "int default 0")
	private int anonymous;
	
	@Transient
	private int like_count;
	
	@Transient
	private boolean like_check;
	
	@Transient
	private boolean auth;
	
	 // 외래키 설정
	@ManyToOne()
	@JoinColumn(name = "pno", referencedColumnName="pno", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_comment_pno"))
	private Post post;

	// 외래키 설정
	@ManyToOne()
	@JoinColumn(name = "id", referencedColumnName="id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_comment_id"))
	private User user;
	
	@OneToMany(mappedBy="comment")
	private List<Likes> like;

}
