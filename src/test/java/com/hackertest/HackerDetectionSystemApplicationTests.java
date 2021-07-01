package com.hackertest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class HackerDetectionSystemApplicationTests {

	@Autowired
	MockMvc mock;
	
	@Test
	void testSigninSucess() throws Exception {
		mock.perform(post("/parseLine")
			.contentType(MediaType.TEXT_PLAIN_VALUE)
			.content(new StringBuffer()
						.append("80.238.9.179,")
						.append(new Date().getTime())
						.append(",SIGNIN_SUCCESS,Will.Smith").toString())
		).andExpect(status().isOk())
		 .andExpect(jsonPath("$").doesNotExist());
	}
	
	@Test
	void testFirstSigninFailure() throws Exception {
		mock.perform(post("/parseLine")
			.contentType(MediaType.TEXT_PLAIN_VALUE)
			.content(new StringBuffer()
						.append("80.238.9.179,")
						.append(new Date().getTime())
						.append(",SIGNIN_FAILURE,Will.Smith").toString())
		).andExpect(status().isOk())
		 .andExpect(jsonPath("$").doesNotExist());
	}
	
	@Test
	void testSecondSigninFailure() throws Exception {
		
		for (int repeat=2; repeat>0; repeat--) {
			mock.perform(post("/parseLine")
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.content(new StringBuffer()
							.append("2.2.2.2,")
							.append(new Date().getTime())
							.append(",SIGNIN_FAILURE,Will.Smith").toString())
			).andExpect(status().isOk())
			 .andExpect(jsonPath("$").doesNotExist());
		}
	}
	
	@Test
	void testThirdSigninFailure() throws Exception {
		
		for (int repeat=3; repeat>0; repeat--) {
			mock.perform(post("/parseLine")
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.content(new StringBuffer()
							.append("3.3.3.3,")
							.append(new Date().getTime())
							.append(",SIGNIN_FAILURE,Will.Smith").toString())
			).andExpect(status().isOk())
			 .andExpect(jsonPath("$").doesNotExist());
		}
	}
	
	@Test
	void testFourthSigninFailure() throws Exception {
		
		for (int repeat=4; repeat>0; repeat--) {
			mock.perform(post("/parseLine")
					.contentType(MediaType.TEXT_PLAIN_VALUE)
					.content(new StringBuffer()
							.append("4.4.4.4,")
							.append(new Date().getTime())
							.append(",SIGNIN_FAILURE,Will.Smith").toString())
				).andExpect(status().isOk())
				 .andExpect(jsonPath("$").doesNotExist());
		}	
	}
	
	@Test
	void testFifthSigninFailure() throws Exception {
		
		for (int repeat=4; repeat>0; repeat--) {
			mock.perform(post("/parseLine")
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.content(new StringBuffer()
							.append("5.5.5.5,")
							.append(new Date().getTime())
							.append(",SIGNIN_FAILURE,Will.Smith").toString())
			).andExpect(status().isOk())
			 .andExpect(jsonPath("$").doesNotExist());
		}
		
		mock.perform(post("/parseLine")
			.contentType(MediaType.TEXT_PLAIN_VALUE)
			.content(new StringBuffer()
					.append("5.5.5.5,")
					.append(new Date().getTime())
					.append(",SIGNIN_FAILURE,Will.Smith").toString())
		).andExpect(status().isOk())
		 .andExpect(content().string("5.5.5.5"));
	}
	
	@Order(1)
	@Test
	void testFifthSigninFailureAfter4Minutes() throws Exception {
		
		for (int repeat=4; repeat>0; repeat--) {
			mock.perform(post("/parseLine")
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.content(new StringBuffer()
							.append("6.6.6.6,")
							.append(new Date().getTime())
							.append(",SIGNIN_FAILURE,Will.Smith").toString())
			).andExpect(status().isOk())
			 .andExpect(jsonPath("$").doesNotExist());
		}
		//sleep 4 minutes
		Thread.sleep(240000);
		
		mock.perform(post("/parseLine")
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.content(new StringBuffer()
						.append("6.6.6.6,")
						.append(new Date().getTime())
						.append(",SIGNIN_FAILURE,Will.Smith").toString())
			).andExpect(status().isOk())
			 .andExpect(content().string("6.6.6.6"));
	}
	
	@Test
	void testFifthSigninFailureAfter5Minutes() throws Exception {
		
		for (int repeat=4; repeat>0; repeat--) {
			mock.perform(post("/parseLine")
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.content(new StringBuffer()
							.append("7.7.7.7,")
							.append(new Date().getTime())
							.append(",SIGNIN_FAILURE,Will.Smith").toString())
			).andExpect(status().isOk())
			 .andExpect(jsonPath("$").doesNotExist());
		}
		//sleep > 5 minutes
		Thread.sleep(301000);
		
		mock.perform(post("/parseLine")
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.content(new StringBuffer()
						.append("7.7.7.7,")
						.append(new Date().getTime())
						.append(",SIGNIN_FAILURE,Will.Smith").toString())
			).andExpect(status().isOk())
		 	.andExpect(jsonPath("$").doesNotExist());
	}

}
