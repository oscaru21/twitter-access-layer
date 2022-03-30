package com.oumana.repository;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.oumana.entity.Tweet;

@DataJpaTest
public class TweetRepositoryTests {
	@Autowired
	private TweetRepository tweetRepository;
	
	//Junit test for save tweet operation
	@DisplayName("Junit test for save tweet operation")
	@Test
	public void givenTweetObject_whenSave_thenReturnSavedTweet() {
		//given
		Tweet tweet = Tweet.builder()
				.name("Oscar Umaña")
				.username("oscar_umana")
				.imgUrl("www.twitter.com")
				.profileImgUrl("www.twitter.com")
				.text("this is a tweet")
				.createdAt(LocalDate.now())
				.build();
		
		//when
		Tweet savedTweet = tweetRepository.save(tweet);
		
		//then
		Assertions.assertThat(savedTweet).isNotNull();
		Assertions.assertThat(savedTweet.getId()).isGreaterThan(0);
	}
}
