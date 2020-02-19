package com.ssafysns.model.service;

import java.util.List;

import javax.transaction.Transactional;

import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.exception.LikesException;
import com.ssafysns.exception.VoteException;
import com.ssafysns.model.dto.Vote;
import com.ssafysns.model.dto.VoteRecord;
import com.ssafysns.repository.LikesRepository;
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
	
	/**
	 * Vote Record
	 */
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
		}
	}
	
	// 내가 투표를 했는지 안했는지 체크
	@Override
	public Vote isVoted(String id, int vno) {
		Vote vote = null;
		try {
			vote = voteRecordRepository.isVoted(id, vno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vote;
	}


	/**
	 * Vote
	 */
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

	@Override
	public List<Boolean> voteBooleanPost(List<Integer> vote_post, List<Integer> follow_list) {
		List<Boolean> boolean_list = null;
		
		try {
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

	@Override
	public Vote searchByPno(int pno) {
		Vote vote = null;
		try {
			vote = voteRepository.findByPno(pno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vote;
	}

}
