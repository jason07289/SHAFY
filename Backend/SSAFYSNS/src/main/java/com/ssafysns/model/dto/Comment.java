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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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
@Entity(name = "comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY로 해야 Auto Increment
	@Column(nullable = false, unique = true)
	private int cno;

	@Column(nullable = false)
	private int pno;

	@Column(columnDefinition="default null")
	private Integer parent;

	@Column(nullable = false, length = 30)
	private String id;
	
	@Column(nullable = false, length = 20)	//게시글 등록 시 직접 입력
	private String nickname;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;

	@Column(nullable = false, length = 300)
	private String content;

	@Column(columnDefinition="default 0")
	private int deleted;
	
	@Transient
	private int like_count;
	
	@Transient
	private boolean like_check;

	 // 외래키 설정
	@ManyToOne
	@JoinColumn(name = "pno", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_comment_pno"))
	private Post post;
 
	// 외래키 설정
//	@OneToMany(mappedBy = "comments")
//	private List<Comments> comments = new ArrayList<Comments>();

	// 외래키 설정
//	@ManyToOne
//	@JoinColumn(name = "parent", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_comment_parent"))
//	private Comment comment;

	// 외래키 설정
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_comment_id"))
	private User user;
	
	@OneToMany(mappedBy="comment")
	private List<Likes> like;

}
