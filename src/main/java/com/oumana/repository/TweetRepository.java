package com.oumana.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.oumana.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long>{
	List<Tweet> findByUsername(String username);
	
	@Query("select t from Tweet t where t.text like CONCAT('%', ?1, '%')")
	List<Tweet> findTweetsLikeText(String text);
}
