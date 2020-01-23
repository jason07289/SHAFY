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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
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
@ToString
@Entity
@Table(name = "post")
public class Post {
	@Id
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

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;

	@Column()
	private int views;

	@Column()
	private int likes;

	@Column(length = 200)
	private String attachments;

	@Column(columnDefinition = "TINYINT", length = 1)
	private int deleted;

//	// 외래키 설정
//	@OneToMany(mappedBy="post")
//	@JoinColumn(name="no")
//	private List<Comment> comments = new ArrayList<Comment>();

	// 외래키 설정
	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_board_id"))
	private User user;
}
