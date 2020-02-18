package com.ssafysns.model.service;

import javax.transaction.Transactional;

import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.exception.LikesException;
import com.ssafysns.exception.VoteException;
import com.ssafysns.model.dto.Vote;
import com.ssafysns.model.dto.VoteRecord;
import com.ssafysns.repository.VoteRecordRepository;
import com.ssafysns.repository.VoteRepository;

@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	VoteRepository voteRepository;
	
	@Autowired
	VoteRecordRepository voteRecordRepository;
	
	@Autowired
	JwtService jwtService;
	
	@Override
	public void insert(VoteRecord voteRecord) {
		try {
			voteRecordRepository.save(voteRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean update(VoteRecord voteRecord) throws UserException {
		String jwtId = null;
		try {
			jwtId  = jwtService.get("userid");
			if(jwtId.equals(voteRecord.getId())) {
				voteRecordRepository.save(voteRecord);
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Transactional
	@Override
	public void delete(VoteRecord voteRecord) {
		try {
			voteRecordRepository.deleteVotes(voteRecord.getVno(), voteRecord.getId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new VoteException("");
		}
	}

	@Override
	public void insert(Vote vote) {
		try {
			voteRepository.save(vote);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int searchByUserId(String id) {
		int vno = -1;
		try {
			vno = voteRepository.searchByUserId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vno;
	}

	@Override
	public boolean update(Vote vote) {
		try {
			voteRepository.save(vote);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
