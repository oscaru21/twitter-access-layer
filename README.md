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
//JUnit test for - operation
@Test
@DisplayName("")
public void given_when_then() {
	//given - precondition or setup
		
	//when - behaviour that we want to test
		
	//then - verify the output
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

## Create Soring Data custom query method with JPQL with index parameters.
JPQL stands for Java Peristance Query Language so in order to make a custom query we need to use the @Query annotation with a very similar syntax to SQL, bt instead of using the tables and columns names, we will use the class and attributes names to assign values to the query we se the index parameters that are like this "?1 or ?2".
```java
@Query("select t from Tweet t where t.text like CONCAT('%', ?1, '%')")
List<Tweet> findTweetsLikeText(String text);
```

## Create Soring Data custom query method with JPQL with name parameters.
For this query we will use custom names for query parameters in order to insert them in the query we will write a colon followed by the name :param, and we will also define this name with the @Param annotation.

```java
@Query("select t from Tweet t where t.text like CONCAT('%', :text, '%')")
List<Tweet> findTweetsLikeTextNamed(@Param("text") String text);
```

## Native SQL querys.
We can use native sql querys with the @Query annotation as well but we need to use the table and columns name and specify that we are using native SQL like this.
```java
@Query(value = "select * from tweets t where t.text = ?1", nativeQuery = true)
List<Tweet> findTweetsLikeText(String text);
```

## @BeforeEach JUnit annotation.
We can use this annotation to create commons instances or initial setup for every test.}

# Mockito
We have two different ways two mock dependencies using mockito
- @Autowired dependency injection and @InjectMocks @Mock annotations
```java
//class under test
@Service

public class TweetService {
	@Autowired
	private TweetRepository tweetRepository;
}

//test class
@EntedWith(MockitoExtension.class)
public class TweetServiceTests {
	@Mock
	private TweetRepository tweetRepository;
	@InjectMocks
	private TweetService tweetService;
}
```   
- Constructor dependency injection and mock() method.
```java
//class under test
@Service
@RequiredArgsConstructor
public class TweetService {
	private final TweetRepository tweetRepository;
}

//test class
public class TweetServiceTests {
	private TweetRepository tweetRepository;
	private TweetService tweetService;
	
	@BeforeEach
	public void setup(){
		tweetRepository = Mockito.mock(TweetRepository.class);
		tweetService = new TweetService(tweetRepository);
	}
}
```   
## Controller layer unit testing
To test the controller layer we will use the @WebMvcTest annotation. Using this annotation will disable full auto-configuration and instead apply only configuration relevant to MVC tests (i.e. @Controller, @ControllerAdvice, @JsonComponent, Converter/GenericConverter, Filter, WebMvcConfigurer and HandlerMethodArgumentResolver beans but not @Component, @Service or @Repository beans).

In order to test each of the REST API methods we have to mock tehm using the MockMvc class perform method that perform a request and return a type that allows chaining further. We can define this type using the contentType method and the content methods to set the content itself.

```java
mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/tweets")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(tweet)));
```

We will test the response in order to see if the http status is the one that we expect using the MovkMvcResultMatchers.status() and compare each of the JSON response fields using the MockMvcResultMatchers.jsonPath("$.parameter", CoreMatchers.is()):
```java
response.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(tweet.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(tweet.getUsername())));
```

