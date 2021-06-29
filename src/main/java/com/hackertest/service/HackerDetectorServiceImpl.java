package com.hackertest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackertest.model.SigninFailure;
import com.hackertest.repo.SigninFailureRepo;

@Service
public class HackerDetectorServiceImpl implements HackerDetectionService {

	@Autowired
	SigninFailureRepo signinFailureRepo;
	
	@Override
	public void saveSigninFailure(SigninFailure signinFailure) {
		signinFailureRepo.save(signinFailure);
	}

	@Override
	public boolean suspiciousIp(SigninFailure signinFailure) {
		boolean suspicious = Boolean.FALSE;
		
		//if there are 5 failures signin en last five minutes return true
		if (signinFailureRepo.countByIpAndTimestampGreaterThan(
				signinFailure.getIp(),signinFailure.getTimestamp()-300000) >= 5) {
			suspicious = Boolean.TRUE;
		}
		return suspicious;
	}

}
