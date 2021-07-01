package com.hackertest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hackertest.model.SigninFailure;

@Repository
public class SigninFailureDaoImpl implements SigninFailureDao {

	@Autowired
	SigninFailureRepo signinFailureRepo;
	
	@Override
	public void save(SigninFailure signinFailure) {
		signinFailureRepo.save(signinFailure);
	}

	@Override
	public long countLastSigninFailures(String ip, long timestamp) {
		return signinFailureRepo.countByIpAndTimestampGreaterThan(ip,timestamp);
	}

	@Override
	public SigninFailure getFirstSigninFailureSince(String ip, long timestamp) {
		return signinFailureRepo.findFirst1ByIpAndTimestampGreaterThanOrderByTimestampAsc(ip, timestamp);
	}

}
