package springdemo.jokesapp3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import springdemo.jokesapp3.entity.Joke;
import springdemo.jokesapp3.entity.JokeHelp;
import springdemo.jokesapp3.entity.Role;
import springdemo.jokesapp3.entity.User;
import springdemo.jokesapp3.service.JokeService;
import springdemo.jokesapp3.service.RoleService;
import springdemo.jokesapp3.service.UserService;
@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	
	@InjectMocks
	UserController userController;
	
	@Mock
	UserService userService;
	
	@Mock 
	RoleService roleService;
	
	@Mock
	Model model;
	
	@Mock
	HttpSession httpSession;

	
	@Test
	public void listUsersTest() throws Exception {
		User user = new User();
		List<User> usersList = new ArrayList<>();
		usersList.add(user);
		Page<User> users = new PageImpl<User>(usersList);
		
		Mockito.when(userService.findByName("", PageRequest.of(0, 10))).thenReturn(users);
		String viewName = userController.listUsers(model, 0, "");
		assertEquals("list-users", viewName);
		
		ArgumentCaptor<Page<User>> argumentCaptor = ArgumentCaptor.forClass(Page.class);
		
		Mockito.verify(userService, Mockito.times(1)).findByName("", PageRequest.of(0, 10));
		Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("users"),  argumentCaptor.capture());
		
		Page<User> users2 = argumentCaptor.getValue();
		
		assertEquals(1, users2.getNumberOfElements());
			
	}
	
	@Test
	public void changeUserStatusTest() throws Exception {
		User user = new User();
		Role role = new Role();
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		
		Mockito.when(roleService.findAll()).thenReturn(roles);
		String viewName = userController.changeUserStatus(model, "email", 0, httpSession);
		assertEquals("/change-user-status", viewName);
		
		ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
		ArgumentCaptor<List<Role>> argumentCaptor2 = ArgumentCaptor.forClass(List.class);
		
		Mockito.verify(userService, Mockito.times(1)).findOne("email");
		Mockito.verify(roleService, Mockito.times(1)).findAll();
		Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("user"),  argumentCaptor.capture());
		Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("curRoles"),  argumentCaptor2.capture());
		
		
		assertEquals(1, argumentCaptor2.getValue().size());
			
	}
	
	
	
	
	

}
