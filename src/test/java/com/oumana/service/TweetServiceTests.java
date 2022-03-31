package com.oumana.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.oumana.entity.Tweet;
import com.oumana.exception.ResourceNotFoundException;
import com.oumana.repository.TweetRepository;

@ExtendWith(MockitoExtension.class)
public class TweetServiceTests {
	@Mock
	private TweetRepository tweetRepository;
	@InjectMocks
	private TweetService tweetService;

	private Tweet tweet;

	@BeforeEach
	public void setup() {
		tweet = Tweet.builder().id(1L).name("Oscar Umaña").username("oscar_umana").imgUrl("www.twitter.com")
				.profileImgUrl("www.twitter.com").text("this is a tweet").createdAt(LocalDate.now()).build();
	}

	// JUnit test for saveTweet operation
	@Test
	@DisplayName("JUnit test for saveTweet operation")
	public void givenTweetObject_whenSaveTweet_thenReturnTweetObject() {
		// given - precondition or setup
		given(tweetRepository.save(tweet)).willReturn(tweet);

		// when - behaviour that we want to test
		Tweet savedTweet = tweetService.saveTweet(tweet);

		// then - verify the output
		Assertions.assertThat(savedTweet).isNotNull();
	}
	
	//JUnit test for getAllTweets operation
	@Test
	@DisplayName("JUnit test for getAllTweets operation")
	public void givenTweetList_whenGetAllTweets_thenReturnTweetList() {
		//given - precondition or setup
		Tweet tweet2 = Tweet.builder().id(1L).name("Oscar Umaña").username("oscar_umana").imgUrl("www.twitter.com")
				.profileImgUrl("www.twitter.com").text("this is a tweet").createdAt(LocalDate.now()).build();
		
		given(tweetRepository.findAll()).willReturn(List.of(tweet, tweet2));
		
		//when - behaviour that we want to test
		List<Tweet> tweets = tweetService.getAllTweets();
		
		//then - verify the output
		Assertions.assertThat(tweets).isNotNull();
		Assertions.assertThat(tweets.size()).isEqualTo(2);
	}
	
	//JUnit test for getAllTweets operation
	@Test
	@DisplayName("JUnit test for getAllTweets operation with empty list")
	public void givenEmptyTweetList_whenGetAllTweets_thenReturnEmptyTweetList() {
		//given - precondition or setup
		given(tweetRepository.findAll()).willReturn(List.of());
		
		//when - behaviour that we want to test
		List<Tweet> tweets = tweetService.getAllTweets();
		
		//then - verify the output
		Assertions.assertThat(tweets).isEmpty();
		Assertions.assertThat(tweets.size()).isEqualTo(0);
	}
	
	//JUnit test for getTweetById operation
	@Test
	@DisplayName("JUnit test for getTweetById operation")
	public void givenId_whenGetTweetById_thenReturnTweetObject() {
		//given - precondition or setup
		long id = 1L;
		given(tweetRepository.findById(id)).willReturn(Optional.of(tweet));
		
		//when - behaviour that we want to test
		Tweet tweetById = tweetService.getTweetById(id);

		//then - verify the output
		Assertions.assertThat(tweetById).isNotNull();
	}
	
	//JUnit test for getTweetById operation with not existing id
	@Test
	@DisplayName("JUnit test for getTweetById operation with not existing id")
	public void givenIncorrectId_whenGetTweetById_thenThrowException() {
		//given - precondition or setup
		long id = 2L;
		given(tweetRepository.findById(id)).willReturn(Optional.empty());
		
		//when - behaviour that we want to test
		//then - verify the output
		org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			tweetService.getTweetById(id);
		});
	}
	
	//JUnit test for updateTweet operation
	@Test
	@DisplayName("JUnit test for updateTweet operation")
	public void givenTweetObject_whenUpdateTweet_thenReturnUpdatedTweetObject() {
		// given - precondition or setup
		given(tweetRepository.save(tweet)).willReturn(tweet);
		tweet.setText("Updated tweet content.");

		// when - behaviour that we want to test
		Tweet updatedTweet = tweetService.updateTweet(tweet);

		// then - verify the output
		Assertions.assertThat(updatedTweet.getText()).isEqualTo("Updated tweet content.");
	}
	
	//JUnit test for deleteTweet operation
	@Test
	@DisplayName("JUnit test for deleteTweet operation")
	public void givenTweetId_whenDeleteTweet_thenNothing() {
		//given - precondition or setup
		long tweetId = 1L;
		BDDMockito.willDoNothing().given(tweetRepository).deleteById(tweetId);
		
		//when - behaviour that we want to test
		tweetService.deleteTweet(tweetId);
		
		//then - verify the output
		verify(tweetRepository, times(1)).deleteById(tweetId);
	}
}
