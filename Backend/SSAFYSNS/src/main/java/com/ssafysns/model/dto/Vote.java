package com.ssafysns.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
@Entity(name = "vote")
public class Vote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY로 해야 Auto Increment
	@Column(nullable = false, unique = true)
	int vno;
	
	@Column()
	String id;
	
	@Column()
	int pno;
	
	@Column(nullable = false, length = 30)
	String title;
	
	@Column(nullable = false, length = 30)
	String a_name;
	
	@Column(nullable = false, length = 30)
	String b_name; 
	
	@Transient
	int a_value;
	
	@Transient
	int b_value;
//	
	@Column()
	int chk;
//	
	@Transient
	String my_value; 
	
	// 외래키 설정
	@ManyToOne()
	@JoinColumn(name = "pno", referencedColumnName="pno", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_vote_pno"))
	private Post post;
	
	// 외래키 설정
	@OneToOne()
	@JoinColumn(name = "id", referencedColumnName="id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_vote_id"))
	private User user;

}
