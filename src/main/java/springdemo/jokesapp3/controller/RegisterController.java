package springdemo.jokesapp3.controller;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import springdemo.jokesapp3.entity.User;
import springdemo.jokesapp3.service.UserService;
@Slf4j
@Controller
public class RegisterController {
	@Autowired
	private UserService userService;

//	add an initbinder.. to convert trim input strings
//	remove leading and trailing whitespace
//	resolve issue for validation
	@InitBinder
	public void InitBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute("user", new User());
		return "/registerForm";
	}
		
	@PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "/registerForm";
		}
		
		if(userService.isUserPresent(user.getEmail())) {
			model.addAttribute("exist",true);
			return "/registerForm";

		}
		
		userService.createUser(user);
		return "/success";
	}

}
