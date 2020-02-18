package com.ssafysns.model.service;

import org.omg.CORBA.UserException;

import com.ssafysns.model.dto.Vote;
import com.ssafysns.model.dto.VoteRecord;

public interface VoteService {
	
	public void insert(VoteRecord voteRecord);
	public boolean update(VoteRecord voteRecord) throws UserException;
	public void delete(VoteRecord voterecord);
	
	public void insert(Vote vote);
	public boolean update(Vote vote);
	public int searchByUserId(String id);
	
}
