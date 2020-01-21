package com.ssafysns.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ssafysns.model.dto.Post.PostBuilder;

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
@Entity(name = "comments")
public class Comments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY로 해야 Auto Increment
	@Column(nullable = false, unique = true)
	private int no;

	@Column(nullable = false)
	private int pno;

	@Column()
	private int parent;

	@Column(nullable = false, length = 30)
	private String id;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;

	@Column(nullable = false, length = 300)
	private String content;

	@Column()
	private int likes;

	@Column()
	private boolean deleted;

	// 외래키 설정
	@ManyToOne
	@JoinColumn(name = "pno", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_comments_pno"))
	private Post post;

	// 외래키 설정
//	@OneToMany(mappedBy = "comments")
//	private List<Comments> commentss = new ArrayList<Comments>();

	// 외래키 설정
	@ManyToOne
	@JoinColumn(name = "parent", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_comments_parent"))
	private Comments comments;

	// 외래키 설정
	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_comments_id"))
	private User user;

}
