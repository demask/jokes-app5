package springdemo.jokesapp3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import springdemo.jokesapp3.dao.JokeDao;
import springdemo.jokesapp3.entity.Joke;
import springdemo.jokesapp3.entity.User;
import springdemo.jokesapp3.service.JokeService;
import springdemo.jokesapp3.service.UserService;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class JokesAppTests {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JokeService jokeService;
	
	@Autowired
	private JokeDao jokeDao;

	@Before
	public void initDb() {
		{
			User newUser = new User("testUser@mail.com", "testUser", "123456");
			userService.createUser(newUser);
		}
		{
			User newUser = new User("testAdmin@mail.com", "testAdmin", "123456");
			userService.createUser(newUser);
		}	

	}
	
	

	@Test
	public void testUser() {
		User user = userService.findOne("testUser@mail.com");
		assertNotNull(user);
		User admin = userService.findOne("testAdmin@mail.com");
		assertEquals(admin.getEmail(), "testAdmin@mail.com");
	}
	
	@Test
	public void deleteUsers() {
		
		userService.delete("testUser@mail.com");
		userService.delete("testAdmin@mail.com");

		assertFalse(userService.isUserPresent("testAdmin@mail.com"));
		assertFalse(userService.isUserPresent("testUser@mail.com"));
	}

}
