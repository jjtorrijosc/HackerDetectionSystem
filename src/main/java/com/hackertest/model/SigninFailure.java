package com.hackertest.model;

import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class SigninFailure {

	@Indexed
	private String ip;
	private long timestamp;

	//the key expires (mongoDB automatically delete it) after 5 minutes
	@Indexed(name="expire_after_seconds", expireAfterSeconds = 300)
    private Date ttlIndex;
	
	public SigninFailure(String ip, long timestamp) {
		super();
		this.ip = ip;
		this.timestamp = timestamp;
		ttlIndex = new Date();
	}
	
	
}
