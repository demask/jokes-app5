package springdemo.jokesapp3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class IndexController {
	
	@GetMapping("/login") 
	public String showLoginForm() {
		log.info("in IndexController showLoginForm()");
		return "loginForm";  
	}
	

}
