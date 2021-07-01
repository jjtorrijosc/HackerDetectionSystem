package com.hackertest.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackertest.Exception.DateRFC2822FormatException;
import com.hackertest.dao.SigninFailureDao;
import com.hackertest.model.SigninFailure;
import com.hackertest.util.TimeCalculationUtil;

@Service
public class HackerDetectorServiceImpl implements HackerDetectionService {

	private static final long FIVE_MINUTES_MS = 300000;
	
	@Autowired
	SigninFailureDao signinFailureDao;
	
	@Override
	public void saveSigninFailure(SigninFailure signinFailure) {
		signinFailureDao.save(signinFailure);
	}

	@Override
	public boolean suspiciousIp(SigninFailure signinFailure) {
		boolean suspicious = Boolean.FALSE;
		
		//if there are 5 failures signin in the last five minutes return true
		if (signinFailureDao.countLastSigninFailures(
									signinFailure.getIp(),
									signinFailure.getTimestamp()-FIVE_MINUTES_MS) >= 5) {
			suspicious = Boolean.TRUE;
			
			
				//NO NEED MORE OPERATIONS, but as required, use time calculation method (minutesBetween)
			SigninFailure firstIpSigninFailure = signinFailureDao.getFirstSigninFailureSince(
									signinFailure.getIp(),
									signinFailure.getTimestamp()-FIVE_MINUTES_MS);
			
		//  and the first failure signin for this ip happened less than 5 minutes return true
			try {
				if (firstIpSigninFailure != null 
						&& TimeCalculationUtil.minutesBetween(
								TimeCalculationUtil.sdf_RFC_2822.format(new Date(signinFailure.getTimestamp())),
								TimeCalculationUtil.sdf_RFC_2822.format(new Date(firstIpSigninFailure.getTimestamp()))) < 5) {
					suspicious = Boolean.TRUE;
				}
			} catch (DateRFC2822FormatException e) {
				//if cannot compare the timestamps return false
				suspicious = Boolean.FALSE;
			}
		}
		return suspicious;
	}

}
