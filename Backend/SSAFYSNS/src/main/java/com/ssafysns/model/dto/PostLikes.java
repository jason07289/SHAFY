package com.ssafysns.model.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Entity(name = "post_likes")
public class PostLikes { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY로 해야 Auto Increment
	@Column(nullable = false, unique = true)
	private int no;

	@Column(nullable = false) 
	private int pno;
////
	@Column(nullable = false, length = 30)
	private String id;

	 // 외래키 설정
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pno", referencedColumnName="pno", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_postlikes_pno"))
	private Post post;


	// 외래키 설정
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", referencedColumnName="id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_postlikes_id"))
	private User user;
	
	public PostLikes(String id, int pno) {
//		this.getUser().setId(id);
//		this.getPost().setPno(pno);
		this.setId(id);
		this.setPno(pno);
	}

}
