package com.oumana.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oumana.entity.Tweet;
import com.oumana.exception.ResourceNotFoundException;
import com.oumana.service.TweetService;


@WebMvcTest
public class TweetControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TweetService tweetService;

	//JUnit test for createTweet operation
	@Test
	@DisplayName("JUnit test for createTweet operation")
	public void givenTweetObject_whenCreateTweet_thenReturnSavedTweet() throws JsonProcessingException, Exception {
		// given
		Tweet tweet = Tweet.builder().name("Oscar Umaña").username("oscar_umana").imgUrl("www.twitter.com")
				.profileImgUrl("www.twitter.com").text("this is a tweet").createdAt(LocalDate.now()).build();

		BDDMockito.given(tweetService.saveTweet(ArgumentMatchers.any(Tweet.class)))
				.willAnswer((invocation) -> invocation.getArgument(0));

		// when
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/tweets")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(tweet)));

		// then
		response.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(tweet.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(tweet.getUsername())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.profileImgUrl", CoreMatchers.is(tweet.getProfileImgUrl())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.text", CoreMatchers.is(tweet.getText())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.imgUrl", CoreMatchers.is(tweet.getImgUrl())));

	}
	
	//JUnit test for getAllTweets operation
	@Test
	@DisplayName("JUnit test for getAllTweets operation")
	public void givenTweetList_whenGetAllTweets_thenReturnTweetList() throws Exception {
		//given - precondition or setup
		List<Tweet> listOfTweets = new ArrayList<>();
		listOfTweets.add(Tweet.builder().name("Oscar Umaña").username("oscar_umana").imgUrl("www.twitter.com")
				.profileImgUrl("www.twitter.com").text("this is a tweet").createdAt(LocalDate.now()).build());
		listOfTweets.add(Tweet.builder().name("Oscar Umaña").username("oscar_umana").imgUrl("www.twitter.com")
				.profileImgUrl("www.twitter.com").text("this is another tweet").createdAt(LocalDate.now()).build());
		
		BDDMockito.given(tweetService.getAllTweets()).willReturn(listOfTweets);
		
		//when - behaviour that we want to test
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/tweets"));
		
		//then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listOfTweets.size())));
	}
	
	//JUnit test for getTweetById operation
	@Test
	@DisplayName("JUnit test for getTweetById operation")
	public void givenId_whenGetTweetById_thenReturnTweet() throws Exception {
		//given - precondition or setup
		long id = 1L;
		Tweet tweet = Tweet.builder().id(1L).name("Oscar Umaña").username("oscar_umana").imgUrl("www.twitter.com")
				.profileImgUrl("www.twitter.com").text("this is a tweet").createdAt(LocalDate.now()).build();

		BDDMockito.given(tweetService.getTweetById(id)).willReturn(tweet);
		
		//when - behaviour that we want to test
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/tweets/1"));

		//then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(tweet.getName())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(tweet.getUsername())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.profileImgUrl", CoreMatchers.is(tweet.getProfileImgUrl())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.text", CoreMatchers.is(tweet.getText())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.imgUrl", CoreMatchers.is(tweet.getImgUrl())));

	}
	
	//JUnit test for getTweetById negative operation
	@Test
	@DisplayName("JUnit test for getTweetById negative operation")
	public void givenIncorrectId_whenGetTweetById_thenThrowException() throws Exception {
		//given - precondition or setup
		long id = 1L;
		
		BDDMockito.given(tweetService.getTweetById(id)).willThrow(ResourceNotFoundException.class);
		
		//when - behaviour that we want to test
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/tweets/1"));

		//then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	//JUnit test for updateTweet operation
	@Test
	@DisplayName("JUnit test for updateTweet operation")
	public void givenUpdatedTweet_whenUpdateTweet_thenReturnUpdatedTweet() throws Exception {
		//given - precondition or setup
		long id = 1L;
		Tweet savedTweet = Tweet.builder().id(1L).name("Oscar Umaña").username("oscar_umana").imgUrl("www.twitter.com")
				.profileImgUrl("www.twitter.com").text("this is a tweet").createdAt(LocalDate.now()).build();
		Tweet updatedTweet = Tweet.builder().id(1L).name("Oscar Umaña").username("oscar_umana").imgUrl("www.twitter.com")
				.profileImgUrl("www.twitter.com").text("This is an updated tweet").createdAt(LocalDate.now()).build();
		
		BDDMockito.given(tweetService.getTweetById(id)).willReturn(savedTweet);
		BDDMockito.given(tweetService.updateTweet(ArgumentMatchers.any(Tweet.class)))
			.willAnswer((invocation) -> invocation.getArgument(0));
		
		//when - behaviour that we want to test
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/tweets/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedTweet)));
		
		//then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.text", CoreMatchers.is(updatedTweet.getText())));
	}
}
