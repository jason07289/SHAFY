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
@Entity(name = "search")
public class Search {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private int no;

	@Column(nullable = false)
	private String hashtag;

	@Column(nullable = false, length = 30)
	private String id;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date datetime;

	
	@Transient
	private long cnt;
	
	// 외래키 설정
	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_search_id"))
	private User user;
}
