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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ssafysns.model.dto.Vote.VoteBuilder;

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
@Entity(name = "vote_record")
public class VoteRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY로 해야 Auto Increment
	@Column(nullable = false, unique = true)
	int no;
	
	@Column(nullable = false)
	int vno;
	
	@Column(nullable = false, length = 30)
	String id;
	
	// 외래키 설정
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", referencedColumnName="id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_voterecord_id"))
	private User user;
	
	// 외래키 설정
	@ManyToOne()
	@JoinColumn(name = "vno", referencedColumnName="vno", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_voterecord_vno"))
	private Vote vote;

}
