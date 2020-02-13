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
@Table(name = "notification")
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private int no;

	@Column(length = 30, nullable = false)
	// 알람이 발생한 유저의 id
	private String id;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP) // TIMESTAMP:날짜시분초
	@CreationTimestamp
	// id에 해당하는 유저에게 댓글, 좋아요가 발생한 시간
	private Date datetime;

	@Column(nullable = false)
	// 댓글, 좋아요가 발생한 게시물
	private int pno;

	@Column(nullable = false)
	// 좋아요: 1/댓글: 2/대댓글: 3
	private int state;

	@Column(nullable = false, columnDefinition = "int default 0")
	// 알람 확인여부 0: false, 1: true
	private int checked;

	@Column(length = 300)
	private String comment;

	public Notification(String id, Date datetime, int pno, int state, int checked) {
		super();
		this.id = id;
		this.datetime = datetime;
		this.pno = pno;
		this.state = state;
		this.checked = checked;
	}

	public Notification(String id, Date datetime, int pno, int state, int checked, String comment) {
		super();
		this.id = id;
		this.datetime = datetime;
		this.pno = pno;
		this.state = state;
		this.checked = checked;
		this.comment = comment;
	}

	// 외래키 설정
	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_notification_id"))
	private User user;

	// 외래키 설정
	@ManyToOne
	@JoinColumn(name = "pno", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_notification__pno"))
	private Post post;
}
