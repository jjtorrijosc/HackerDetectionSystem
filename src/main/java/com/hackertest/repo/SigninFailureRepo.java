package com.hackertest.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hackertest.model.SigninFailure;

@Repository
public interface SigninFailureRepo extends MongoRepository<SigninFailure, String>{

	@SuppressWarnings("unchecked")
	public SigninFailure save(SigninFailure signinFailure);
	
	public long countByIpAndTimestampGreaterThan(String ip, long timestamp);
}
