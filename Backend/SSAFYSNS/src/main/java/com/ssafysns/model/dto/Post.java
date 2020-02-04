package com.ssafysns.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

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
@Entity
@Table(name = "post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY로 해야 Auto Increment
	@Column(nullable = false, unique = true)
	private int pno;

	@Column(length = 1000, nullable = false)
	private String content;

	@Column(length = 500)
	private String hashtag;

	@Column(nullable=false, length = 30)
	private String id;
	
	@Column(nullable=false, length = 20)	//게시글 등록 시 직접 입력
	private String nickname;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp // Date를 현재 시간으로 초기화하여 저장함
	private Date datetime;

	@Column(length = 200)
	private String attachments;

	@Column(columnDefinition = "int default 0")
	private int deleted;
	
	@Column(columnDefinition = "int default 0")
	private int anonymous;
	
	@Transient
	private int like_count;
	
	@Transient
	private boolean like_check;

	// 외래키 설정
	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_board_id"))
	private User user;
	
	@OneToMany(mappedBy="post")
	private List<Comment> comment;
	
	@OneToMany(mappedBy="post")
	private List<Likes> like;
	
}
