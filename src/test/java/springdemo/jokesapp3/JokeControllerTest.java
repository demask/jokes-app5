package springdemo.jokesapp3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
import springdemo.jokesapp3.entity.Joke;
import springdemo.jokesapp3.entity.JokeHelp;
import springdemo.jokesapp3.service.JokeService;
@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class JokeControllerTest {
	
	@InjectMocks
	JokeController jokeController;
	
	@Mock
	JokeService jokeService;
	
	@Mock
	Model model;
	
	@Mock
	JokeHelp jokeHelp;
	
	@Test
	public void testMockMVC() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(jokeController).build();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("list-jokes"));
			
	}
	
	@Test
	public void getJokesTest() throws Exception {
		
		Joke joke1 = new Joke();
		Joke joke2 = new Joke();
		
		List<Joke> jokesList = new ArrayList<Joke>();
		jokesList.add(joke1);
		jokesList.add(joke2);
		
		Page<Joke> jokes = new PageImpl<Joke>(jokesList);
		
		Mockito.when(jokeService.getJokes(PageRequest.of(0, 10))).thenReturn(jokes);
		
		
		ArgumentCaptor<Page<Joke>> argumentCaptor = ArgumentCaptor.forClass(Page.class);
		ArgumentCaptor<Integer> argumentCaptor2 = ArgumentCaptor.forClass(Integer.class);
		
		String viewName = jokeController.listJokes(model, 0);
		assertEquals("list-jokes", viewName);
		
		Mockito.verify(jokeService, Mockito.times(1)).getJokes(PageRequest.of(0, 10));
		Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("jokes"),  argumentCaptor.capture());
		Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("currentPage"),  argumentCaptor2.capture());
		
		Page<Joke> curPages = argumentCaptor.getValue();
		
		assertEquals(2, curPages.getNumberOfElements());
		
		int currentPage =  argumentCaptor2.getValue();
		assertEquals(0,  currentPage);
			
	}
	
	@Test
	public void likeJokeTest() throws Exception {
		
		String viewName = jokeController.likeJoke(1, 0);
		assertEquals("redirect:/?page="+0, viewName);
		
		Mockito.verify(jokeService, Mockito.times(1)).likeJoke(1);
			
	}
	
	@Test
	public void dislikeJokeTest() throws Exception {
		
		String viewName = jokeController.dislikeJoke(1, 0);
		assertEquals("redirect:/?page="+0, viewName);
		
		Mockito.verify(jokeService, Mockito.times(1)).dislikeJoke(1);
			
	}
	
	@Test
	public void showJokeFormTest() throws Exception {
		
		String viewName = jokeController.showJokeForm(model);
		assertEquals("new", viewName);
			
	}
	
	@Test
	public void deleteJokeTest() throws Exception {
		
		String viewName = jokeController.deleteJoke(1, 0);
		assertEquals("redirect:/?page="+0, viewName);
			
	}
	
	@Test
	public void saveJokeTest() throws Exception {

		BindingResult bindingResult = new BeanPropertyBindingResult(jokeHelp, "error");
		String viewName = jokeController.saveJoke(jokeHelp, bindingResult, model);
		assertEquals("redirect:/", viewName);	
		
		BindingResult bindingResult2 = new BeanPropertyBindingResult(jokeHelp, "error");
		bindingResult2.addError(new ObjectError("test error", "error"));
		String viewName2 = jokeController.saveJoke(jokeHelp, bindingResult2, model);
		assertEquals("new", viewName2);
	}
	
	
	
	
	

}
