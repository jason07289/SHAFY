package com.ssafysns.model.service;

import java.util.List;

import org.omg.CORBA.UserException;

import com.ssafysns.model.dto.Vote;
import com.ssafysns.model.dto.VoteRecord;

public interface VoteService {

	/**
	 * Vote Record
	 */
	public void insert(VoteRecord voteRecord);
	public boolean update(VoteRecord voteRecord) throws UserException;
	public void delete(VoteRecord voterecord);
	
	// 내가 투표를 어디에 했는지/안했는지 체크
	public Vote isVoted(String id, int vno);
	
	/**
	 * Vote
	 */
	public void insert(Vote vote);
	public boolean update(Vote vote);
	public int searchByUserId(String id);
	
	public List<Boolean> voteBooleanPost(List<Integer> my_like_post, List<Integer> follow_list);
	public Vote searchByPno(int pno);
}
