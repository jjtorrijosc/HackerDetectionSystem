package com.hackertest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hackertest.model.LoginActionEnum;
import com.hackertest.model.SigninFailure;
import com.hackertest.service.HackerDetectionService;
import com.hotelbeds.supplierintegrations.hackertest.detector.HackerDetector;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@Api(value = "Hacker detection service")
@RestController
public class HackerDetectorController implements HackerDetector {

	private static final String LOG_SEPARATOR = ",";
	
	@Autowired
	HackerDetectionService hackerDetectionService;
	
	@Override
	@ApiOperation(value = "parseLine, returns ip of logline if that has attempted a failed login 5 or more times within a 5 minute period", response=String.class)
	@PostMapping(value="parseLine", consumes=MediaType.TEXT_PLAIN_VALUE , produces=MediaType.TEXT_PLAIN_VALUE)
	public String parseLine(
					@ApiParam(value = "log line formatted (ip,date,action,username like this 80.238.9.179,133612947,SIGNIN_SUCCESS,Will.Smith) action=['SIGNIN_SUCCESS' or 'SIGNIN_FAILURE']",required = true)
					@RequestBody(required = true) String line) {
		
		String ipSuspicious = null;
		String [] logLine = line.split(LOG_SEPARATOR);
		
		//if SIGNIN_SUCESS, more actions are not required
		if (logLine.length>3 
					&& LoginActionEnum.SIGNIN_FAILURE.toString().equals(logLine[2])) {
			SigninFailure signinFailure = new SigninFailure(logLine[0].trim(),Long.valueOf(logLine[1]));
			hackerDetectionService.saveSigninFailure(signinFailure);
			if (hackerDetectionService.suspiciousIp(signinFailure)) {
				ipSuspicious = signinFailure.getIp();
			}
		}
		return ipSuspicious;
	}

}
