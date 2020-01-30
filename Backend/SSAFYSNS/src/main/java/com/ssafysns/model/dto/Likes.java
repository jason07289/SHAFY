package com.ssafysns.model.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
@Entity(name = "likes")
public class Likes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY로 해야 Auto Increment
	@Column(nullable = false, unique = true)
	private int no;

	@Column(nullable = false)
	private int pno;
	
	@Column(nullable = false)		// 댓글이 아닌 게시글 좋아요일 경우 cno = -1로 처리 
	private Integer cno;

	@Column(nullable = false, length = 30)
	private String id;

	 // 외래키 설정
	@ManyToOne
	@JoinColumn(name = "pno", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_likes_pno"))
	private Post post;

	// 외래키 설정
	@ManyToOne
	@JoinColumn(name = "cno", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_likes_cno"))
	private Comment comment;


	// 외래키 설정
	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_likes_id"))
	private User user;
	
	public Likes(String id, int pno, int cno) {
		this.id = id;
		this.pno = pno;
		this.cno = cno;
	}

}
