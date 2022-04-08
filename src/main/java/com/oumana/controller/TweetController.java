package com.oumana.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oumana.entity.Tweet;
import com.oumana.service.TweetService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/tweets")
public class TweetController {
	
	private final TweetService tweetService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Tweet createTweet(@RequestBody Tweet tweet){
		return tweetService.saveTweet(tweet);
	}
	
	@GetMapping
	public List<Tweet> getAllTweets(){
		return tweetService.getAllTweets();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tweet> getTweetById(@PathVariable long id){
		try {
			return new ResponseEntity<>(tweetService.getTweetById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Tweet> updateTweet(@PathVariable long id, @RequestBody Tweet tweet){
		try {
			Tweet savedTweet = tweetService.getTweetById(id);
			savedTweet.setText(tweet.getText());
			savedTweet.setImgUrl(tweet.getImgUrl());
			return new ResponseEntity<>(tweetService.updateTweet(savedTweet), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
}
