# Spring boot API folder structure
- main package
  - entity
  - repository
  - service
  - controller
  - exception

## Persistence layer tests.
In order to test the persistance layers, spring-test provides the @DataJpaTest annotation that allows us to test only the repositorys with in memory data so we don't have to mock any other bean, for each repository we should at least test the basic CRUD operations writting one test for each operation following the following syntax:

```java
public void given_when_then() {
		//given
		
		//when
		
		//then
	}
```

we can use the assertJ library in order to perform the tests with the Assertions class, for example we can test that the returned object is not null, and that the generated id is greater than 0 like this:
```java
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
```