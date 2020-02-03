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
	private String id;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP) // TIMESTAMP:날짜시분초
	private Date datetime;

	@Column(nullable = false)
	private int pno;

	@Column(nullable = false)
	private int state;

	@Column(nullable = false)
	private boolean checked;

	@Column()
	private boolean deleted;

	// 외래키 설정
	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_notification_id"))
	private User user;

	// 외래키 설정
	@ManyToOne
	@JoinColumn(name = "pno", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_notification__pno"))
	private Post post;
}
