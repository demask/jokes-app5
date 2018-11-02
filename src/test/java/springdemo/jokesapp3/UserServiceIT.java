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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import springdemo.jokesapp3.dao.JokeDao;
import springdemo.jokesapp3.entity.Joke;
import springdemo.jokesapp3.entity.User;
import springdemo.jokesapp3.service.JokeService;
import springdemo.jokesapp3.service.UserService;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceIT {

	@Autowired
	private UserService userService;

	@Before
	public void initDb() {
		{
			User newUser = new User("testUser@mail.com", "testUser", "123456");
			userService.createUser(newUser);
		}
		{
			User newAdmin = new User("testAdmin@mail.com", "testAdmin", "123456");
			userService.createAdmin(newAdmin);
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
		User user = userService.findOne("testUser@mail.com");
		userService.delete("testUser@mail.com");
		
		User admin = userService.findOne("testAdmin@mail.com");
		userService.delete("testAdmin@mail.com");

		assertFalse(userService.isUserPresent("testAdmin@mail.com"));
		assertFalse(userService.isUserPresent("testUser@mail.com"));
	}

}
