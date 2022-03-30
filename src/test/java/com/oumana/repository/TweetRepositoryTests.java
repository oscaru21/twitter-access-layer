package com.oumana.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.oumana.entity.Tweet;

@DataJpaTest
public class TweetRepositoryTests {
	@Autowired
	private TweetRepository tweetRepository;
	
	private Tweet tweet;
	
	@BeforeEach
	public void setup() {
		tweet = Tweet.builder()
			.name("Oscar Umaña")
			.username("oscar_umana")
			.imgUrl("www.twitter.com")
			.profileImgUrl("www.twitter.com")
			.text("this is a tweet")
			.createdAt(LocalDate.now())
			.build();
	}
	
	//Junit test for save tweet operation
	@DisplayName("Junit test for save tweet operation")
	@Test
	public void givenTweetObject_whenSave_thenReturnSavedTweet() {
		//given
		
		//when
		Tweet savedTweet = tweetRepository.save(tweet);
		
		//then
		Assertions.assertThat(savedTweet).isNotNull();
		Assertions.assertThat(savedTweet.getId()).isGreaterThan(0);
	}
	
	//JUnit test for get all tweets operation
	@Test
	@DisplayName("JUnit test for get all tweets operation")
	public void givenTweets_whenFindAll_thenTweetList() {
		//given - precondition or setup
		Tweet tweet2 = Tweet.builder()
				.name("Oscar Umaña")
				.username("oscar_umana")
				.imgUrl("www.twitter.com")
				.profileImgUrl("www.twitter.com")
				.text("this is another tweet")
				.createdAt(LocalDate.now())
				.build();
		
		tweetRepository.save(tweet);
		tweetRepository.save(tweet2);
		
		//when - behaviour that we want to test
		List<Tweet> tweets = tweetRepository.findAll();
		
		//then - verify the output
		Assertions.assertThat(tweets).isNotNull();
		Assertions.assertThat(tweets.size()).isEqualTo(2);
	}
	
	//JUnit test for get by id operation
	@Test
	@DisplayName("JUnit test for get by id operation")
	public void givenTweetObject_whenFindById_thenReturnTweetObject() {
		//given - precondition or setup
		tweetRepository.save(tweet);	
		
		//when - behaviour that we want to test
		Tweet tweetDB = tweetRepository.findById(tweet.getId()).get();
		
		//then - verify the output
		Assertions.assertThat(tweetDB).isNotNull();
		Assertions.assertThat(tweetDB).isEqualTo(tweet);
	}
	
	//JUnit test for get all tweets by username operation
	@Test
	@DisplayName("JUnit test for get all tweets by username operation")
	public void givenTweets_whenFindByUsername_thenTweetList() {
		//given - precondition or setup
		Tweet tweet2 = Tweet.builder()
				.name("Oscar Umaña")
				.username("oscar_umana")
				.imgUrl("www.twitter.com")
				.profileImgUrl("www.twitter.com")
				.text("this is another tweet")
				.createdAt(LocalDate.now())
				.build();
		
		Tweet tweet3 = Tweet.builder()
				.name("Oscar Umaña")
				.username("another_user")
				.imgUrl("www.twitter.com")
				.profileImgUrl("www.twitter.com")
				.text("this is another tweet")
				.createdAt(LocalDate.now())
				.build();
		
		tweetRepository.save(tweet);
		tweetRepository.save(tweet2);
		tweetRepository.save(tweet3);
		
		//when - behaviour that we want to test
		List<Tweet> tweets = tweetRepository.findByUsername("oscar_umana");
		
		//then - verify the output
		Assertions.assertThat(tweets).isNotNull();
		Assertions.assertThat(tweets.size()).isEqualTo(2);
	}
	
	//Junit test for update tweet operation
	@DisplayName("Junit test for update tweet operation")
	@Test
	public void givenTweetObject_whenUpdateTweet_thenReturnUpdatedTweet() {
		//given
		tweetRepository.save(tweet);
		
		//when
		Tweet savedTweet = tweetRepository.findById(tweet.getId()).get();
		savedTweet.setText("tweet updated content");
		Tweet updatedTweet = tweetRepository.save(savedTweet);
		
		//then
		Assertions.assertThat(updatedTweet).isNotNull();
		Assertions.assertThat(updatedTweet.getText()).isEqualTo("tweet updated content");
	}
	
	//JUnit test for delete tweet operation
	@Test
	@DisplayName("JUnit test for delete tweet operation")
	public void givenTweetObject_whenDelete_thenRemoveTweet() {
		//given - precondition or setup
		tweetRepository.save(tweet);
		
		//when - behaviour that we want to test
		tweetRepository.deleteById(tweet.getId());
		Optional<Tweet> optionalTweet = tweetRepository.findById(tweet.getId());
		
		//then - verify the output
		Assertions.assertThat(optionalTweet).isEmpty();
	}
	
	//JUnit test for find tweet with text like operation 
	@Test
	@DisplayName("JUnit test for find tweet with text like operation")
	public void givenText_whenFindTweetLikeText_thenReturnTweetList() {
		//given - precondition or setup
		Tweet tweet2 = Tweet.builder()
				.name("Oscar Umaña")
				.username("oscar_umana")
				.imgUrl("www.twitter.com")
				.profileImgUrl("www.twitter.com")
				.text("this is another tweet")
				.createdAt(LocalDate.now())
				.build();
		
		tweetRepository.save(tweet);
		tweetRepository.save(tweet2);
		
		String text = "tweet";
		
		//when - behaviour that we want to test
		List<Tweet> tweetsLikeText = tweetRepository.findTweetsLikeText(text);
		
		//then - verify the output
		Assertions.assertThat(tweetsLikeText).isNotNull();
		Assertions.assertThat(tweetsLikeText.size()).isEqualTo(2);
	}
}
