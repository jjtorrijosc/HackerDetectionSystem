package com.hackertest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.hackertest.dao.SigninFailureRepo;

@SpringBootTest
@AutoConfigureMockMvc
class HackerDetectionSystemApplicationTests {

	@Autowired
	MockMvc mock;
	
	@Autowired
	SigninFailureRepo signinFailureRepo;
	
	@Order(1)
	@Test
	void testSigninSucess() throws Exception {
		signinFailureRepo.deleteAll();
		Long timeIni = Calendar.getInstance().getTimeInMillis();
		mock.perform(post("/parseLine")
			.contentType(MediaType.TEXT_PLAIN_VALUE)
			.content(new StringBuffer()
						.append("80.238.9.179,")
						.append(timeIni)
						.append(",SIGNIN_SUCCESS,Will.Smith").toString())
		).andExpect(status().isOk())
		 .andExpect(jsonPath("$").doesNotExist());
	}
	
	@Order(2)
	@Test
	void testFirstSigninFailure() throws Exception {
		Long timeIni = Calendar.getInstance().getTimeInMillis();
		mock.perform(post("/parseLine")
			.contentType(MediaType.TEXT_PLAIN_VALUE)
			.content(new StringBuffer()
						.append("80.238.9.179,")
						.append(timeIni)
						.append(",SIGNIN_FAILURE,Will.Smith").toString())
		).andExpect(status().isOk())
		 .andExpect(jsonPath("$").doesNotExist());
	}
	
	@Order(3)
	@Test
	void testSecondSigninFailure() throws Exception {
		
		Long time = Calendar.getInstance().getTimeInMillis();
		for (int repeat=2; repeat>0; repeat--) {
			mock.perform(post("/parseLine")
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.content(new StringBuffer()
							.append("2.2.2.2,")
							.append(time)
							.append(",SIGNIN_FAILURE,Will.Smith").toString())
			).andExpect(status().isOk())
			 .andExpect(jsonPath("$").doesNotExist());

			//increment timestamp
			time+=1000;
		}
	}
	
	@Order(4)
	@Test
	void testThirdSigninFailure() throws Exception {
		
		Long time = Calendar.getInstance().getTimeInMillis();
		for (int repeat=3; repeat>0; repeat--) {
			mock.perform(post("/parseLine")
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.content(new StringBuffer()
							.append("3.3.3.3,")
							.append(time)
							.append(",SIGNIN_FAILURE,Will.Smith").toString())
			).andExpect(status().isOk())
			 .andExpect(jsonPath("$").doesNotExist());
			
			//increment timestamp
			time+=1000;
		}
	}
	
	@Order(5)
	@Test
	void testFourthSigninFailure() throws Exception {
		
		Long time = Calendar.getInstance().getTimeInMillis();
		for (int repeat=4; repeat>0; repeat--) {
			mock.perform(post("/parseLine")
					.contentType(MediaType.TEXT_PLAIN_VALUE)
					.content(new StringBuffer()
							.append("4.4.4.4,")
							.append(time)
							.append(",SIGNIN_FAILURE,Will.Smith").toString())
				).andExpect(status().isOk())
				 .andExpect(jsonPath("$").doesNotExist());
			
			//increment timestamp
			time+=1000;
		}	
	}
	
	@Order(6)
	@Test
	void testFifthSigninFailure() throws Exception {
		
		Long time = Calendar.getInstance().getTimeInMillis();
		for (int repeat=4; repeat>0; repeat--) {
			mock.perform(post("/parseLine")
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.content(new StringBuffer()
							.append("5.5.5.5,")
							.append(time)
							.append(",SIGNIN_FAILURE,Will.Smith").toString())
			).andExpect(status().isOk())
			 .andExpect(jsonPath("$").doesNotExist());
			
			//increment timestamp
			time+=1000;
		}
		
		mock.perform(post("/parseLine")
			.contentType(MediaType.TEXT_PLAIN_VALUE)
			.content(new StringBuffer()
					.append("5.5.5.5,")
					.append(time)
					.append(",SIGNIN_FAILURE,Will.Smith").toString())
		).andExpect(status().isOk())
		 .andExpect(content().string("5.5.5.5"));
	}
	
	@Order(7)
	@Test
	void testFifthSigninFailureAfter4Minutes() throws Exception {
		
		Long time = Calendar.getInstance().getTimeInMillis();
		for (int repeat=4; repeat>0; repeat--) {
			mock.perform(post("/parseLine")
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.content(new StringBuffer()
							.append("6.6.6.6,")
							.append(time)
							.append(",SIGNIN_FAILURE,Will.Smith").toString())
			).andExpect(status().isOk())
			 .andExpect(jsonPath("$").doesNotExist());
			
			//increment timestamp
			time+=1000;
		}
		//sleep 4 minutes
//		Thread.sleep(240000);
		time+=240000;
		
		mock.perform(post("/parseLine")
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.content(new StringBuffer()
						.append("6.6.6.6,")
						.append(time)
						.append(",SIGNIN_FAILURE,Will.Smith").toString())
			).andExpect(status().isOk())
			 .andExpect(content().string("6.6.6.6"));
	}
	
	@Order(8)
	@Test
	void testFifthSigninFailureAfter5Minutes() throws Exception {
		
		Long time = Calendar.getInstance().getTimeInMillis();
		for (int repeat=4; repeat>0; repeat--) {
			mock.perform(post("/parseLine")
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.content(new StringBuffer()
							.append("7.7.7.7,")
							.append(time)
							.append(",SIGNIN_FAILURE,Will.Smith").toString())
			).andExpect(status().isOk())
			 .andExpect(jsonPath("$").doesNotExist());
			
			//increment timestamp
			time+=1000;
		}
		//sleep > 5 minutes
//		Thread.sleep(301000);
		time+=301000;
		
		mock.perform(post("/parseLine")
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.content(new StringBuffer()
						.append("7.7.7.7,")
						.append(time)
						.append(",SIGNIN_FAILURE,Will.Smith").toString())
			).andExpect(status().isOk())
		 	.andExpect(jsonPath("$").doesNotExist());
	}

}
