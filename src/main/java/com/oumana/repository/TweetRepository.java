package com.oumana.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oumana.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long>{

}
