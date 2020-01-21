package com.ssafysns.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
@Data
@Entity
@Table(name="notice")
public class Notice {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(length=30, nullable=false, unique=true)
	private int no;
	
	@Column(length=50, nullable=false)
	private String title;
	
	@Column(length=1000, nullable=false)
	private String content;
	
	@Column(length=30, nullable=false)
	private String id;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	@Column(nullable=false)
	private Date datetime;
	
	@Column(nullable=false)
	private boolean deleted;
	
}
