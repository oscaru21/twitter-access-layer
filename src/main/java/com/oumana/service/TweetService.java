package com.oumana.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oumana.entity.Tweet;
import com.oumana.exception.ResourceNotFoundException;
import com.oumana.repository.TweetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetService {

	private final TweetRepository tweetRepository;

	public Tweet saveTweet(Tweet tweet) {
		return tweetRepository.save(tweet);
	}

	public List<Tweet> getAllTweets() {
		return tweetRepository.findAll();
	}

	public Tweet getTweetById(long id) {
		return tweetRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No tweets with provided id"));
	}
	
	public Tweet updateTweet(Tweet tweet) {
		return tweetRepository.save(tweet);
	}
	
	public void deleteTweet(long id) {
		tweetRepository.deleteById(id);
	}
}
