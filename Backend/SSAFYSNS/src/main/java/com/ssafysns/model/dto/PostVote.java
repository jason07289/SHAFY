package com.ssafysns.model.dto;

import java.util.Date;
import java.util.List;

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
public class PostVote {
	Vote vote;
	Post post;
}
