package springdemo.jokesapp3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;

import org.hibernate.annotations.Any;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.AbstractBindingResult;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.InitBinder;

import lombok.extern.slf4j.Slf4j;
import springdemo.jokesapp3.controller.IndexController;
import springdemo.jokesapp3.controller.JokeController;
import springdemo.jokesapp3.controller.UserController;
import springdemo.jokesapp3.dao.JokeDAOImpl;
import springdemo.jokesapp3.entity.Category;
import springdemo.jokesapp3.entity.Joke;
import springdemo.jokesapp3.entity.JokeHelp;
import springdemo.jokesapp3.entity.Role;
import springdemo.jokesapp3.entity.User;
import springdemo.jokesapp3.service.JokeService;
import springdemo.jokesapp3.service.RoleService;
import springdemo.jokesapp3.service.UserService;
@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class JokeDAOImplTest {
	
	@InjectMocks
	JokeDAOImpl jokeDAOImpl;
	
	@Mock
	EntityManager entityManager;
	
	@Mock
	TypedQuery<Joke> theJokequery;
	
	@Mock
	TypedQuery<Category> theQuery;
	
	

	@Test
	public void getJokesTest() throws Exception {
		
		List<Joke> jokes = new ArrayList<>();
		for(int i = 0; i < 42; i++) {
			jokes.add(new Joke());
		}
		
		Mockito.when( entityManager.createQuery("from Joke order by ((likes-dislikes), likes) desc", Joke.class))
		.thenReturn(theJokequery);
		Mockito.when(theJokequery.getResultList()).thenReturn(jokes);
		
		
		assertEquals(10, jokeDAOImpl.getJokes(PageRequest.of(2, 10)).getNumberOfElements());
		assertEquals(2, jokeDAOImpl.getJokes(PageRequest.of(2, 10)).getNumber());
		assertEquals(5, jokeDAOImpl.getJokes(PageRequest.of(2, 10)).getTotalPages());
		assertEquals(2, jokeDAOImpl.getJokes(PageRequest.of(4, 10)).getNumberOfElements());
		
		
	}
	
	@Test
	public void saveJokeTest() throws Exception {
		Joke theJoke = new Joke();
		
		List<Category> categories = new ArrayList<>();
		Category category = new Category();
		category.setName("test");
		theJoke.setCategory(category);
		
		
		Mockito.when(entityManager.
				createQuery("from Category where lower(name) like '%" + theJoke.getCategory().getName() + "%'",
				Category.class))
		.thenReturn(theQuery);	
		Mockito.when(theQuery.getResultList()).thenReturn(categories);
				
		jokeDAOImpl.saveJoke(theJoke);
		
		Mockito.verify(entityManager, Mockito.times(1)).persist(theJoke);
		Mockito.verify(entityManager, Mockito.times(1)).persist(Mockito.any(Category.class));
		
		categories.add(category);
		jokeDAOImpl.saveJoke(theJoke);
		
		Mockito.verify(entityManager, Mockito.times(2)).persist(theJoke);
		Mockito.verify(entityManager, Mockito.times(1)).persist(Mockito.any(Category.class));
	}
	
	
	
	
	

}
