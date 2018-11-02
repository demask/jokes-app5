package springdemo.jokesapp3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import springdemo.jokesapp3.dao.JokeDao;
import springdemo.jokesapp3.entity.Category;
import springdemo.jokesapp3.entity.Joke;
import springdemo.jokesapp3.entity.User;
import springdemo.jokesapp3.service.JokeService;
import springdemo.jokesapp3.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JokeDaoIT {

	@Autowired
	private JokeDao jokeDao;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Test
	public void getJokesQueryTest() {
		TypedQuery<Joke> theJokequery = entityManager.createQuery("from Joke order by ((likes-dislikes), likes) desc", Joke.class);
		
		List<Joke> jokes = theJokequery.getResultList();
		assertEquals(42, jokes.size());
	}
	
	@Test
	public void saveJokeTest() {
		Joke joke = new Joke();
		Category category = new Category();
		category.setName("policajci");
		joke.setCategory(category);
		joke.setContent("test");
		jokeDao.saveJoke(joke);
		
		TypedQuery<Joke> theJokequery = entityManager.createQuery("from Joke order by ((likes-dislikes), likes) desc", Joke.class);
		List<Joke> jokes = theJokequery.getResultList();
		assertEquals(43, jokes.size());
		
		TypedQuery<Category> theQuery = entityManager.
				createQuery("from Category",
				Category.class);
		List<Category> listcategory = theQuery.getResultList();
		assertEquals(10, listcategory.size());
		
		Joke joke2 = new Joke();
		Category category2 = new Category();
		category2.setName("new");
		joke2.setCategory(category2);
		joke2.setContent("test");
		jokeDao.saveJoke(joke2);
		
		theJokequery = entityManager.createQuery("from Joke order by ((likes-dislikes), likes) desc", Joke.class);
		jokes = theJokequery.getResultList();
		assertEquals(44, jokes.size());
		
		theQuery = entityManager.
				createQuery("from Category",
				Category.class);
		listcategory = theQuery.getResultList();
		assertEquals(11, listcategory.size());
		
	}
	
}
