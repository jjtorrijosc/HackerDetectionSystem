package com.hackertest.dao;

import com.hackertest.model.SigninFailure;

public interface SigninFailureDao {

	public void save(SigninFailure signinFailure);
	public long countLastSigninFailures(String ip, long timestamp);
	public SigninFailure getFirstSigninFailureSince(String ip, long timestamp);
	
}
