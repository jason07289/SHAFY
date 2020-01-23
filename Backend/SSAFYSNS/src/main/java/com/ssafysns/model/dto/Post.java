package com.ssafysns.model.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	@CreationTimestamp // Date를 현재 시간으로 초기화하여 저장함
	private Date datetime;

	@Column(columnDefinition = "int default 0")
	private int views;

	@Column(columnDefinition = "int default 0")
	private int likes;

	@Column(length = 200)
	private String attachments;

	@Column(columnDefinition = "int default 0")
	private int deleted;

	// 외래키 설정
//	@OneToMany(mappedBy="post")
//    private List<Notification> notifications = new ArrayList<Notification>();

	// 외래키 설정
	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_board_id"))
	private User user;
}
