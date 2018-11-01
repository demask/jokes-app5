package springdemo.jokesapp3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

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
import org.mockito.MockitoFramework;
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

import javassist.expr.Instanceof;
import lombok.extern.slf4j.Slf4j;
import springdemo.jokesapp3.controller.IndexController;
import springdemo.jokesapp3.controller.JokeController;
import springdemo.jokesapp3.controller.RegisterController;
import springdemo.jokesapp3.entity.Joke;
import springdemo.jokesapp3.entity.JokeHelp;
import springdemo.jokesapp3.entity.User;
import springdemo.jokesapp3.service.JokeService;
import springdemo.jokesapp3.service.UserService;
@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest {
	
	@InjectMocks
	RegisterController registerController;
	
	@Mock
	UserService userService;
	
	@Mock
	Model model;
	
	
	@Test
	public void registerUserTest() throws Exception {
		User user = new User();
		Mockito.doNothing().when(userService).createUser(user);
		
		BindingResult bindingResult = new BeanPropertyBindingResult(user, "error");
		bindingResult.addError(new ObjectError("test error", "error"));
		String viewName = registerController.registerUser(user, bindingResult, model);
		assertEquals("/registerForm", viewName);
		
		Mockito.when(userService.isUserPresent(user.getEmail())).thenReturn(true);
		bindingResult = new BeanPropertyBindingResult(user, "error");
		viewName = registerController.registerUser(user, bindingResult, model);
		assertEquals("/registerForm", viewName);
		
		Mockito.when(userService.isUserPresent(user.getEmail())).thenReturn(false);
		bindingResult = new BeanPropertyBindingResult(user, "error");
		viewName = registerController.registerUser(user, bindingResult, model);
		assertEquals("/success", viewName);
		
		
		
	}
	
	@Test
	public void registerFormTest() throws Exception {
		
		String viewName = registerController.registerForm(model);
		assertEquals("/registerForm", viewName);
		
		ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
		Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("user"),  argumentCaptor.capture());
		
			
	}
	
	
	
	

}
