package com.hackertest.service;

import com.hackertest.model.SigninFailure;

public interface HackerDetectionService {

	public void saveSigninFailure(SigninFailure signinFailure);
	public boolean suspiciousIp(SigninFailure signinFailure);
}
