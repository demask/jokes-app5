package springdemo.jokesapp3.controller;

import java.util.List;


import javax.servlet.http.HttpSession;
import javax.sound.midi.MidiDevice.Info;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import springdemo.jokesapp3.entity.Joke;
import springdemo.jokesapp3.entity.Role;
import springdemo.jokesapp3.entity.User;
import springdemo.jokesapp3.repositories.RoleRepository;
import springdemo.jokesapp3.service.RoleService;
import springdemo.jokesapp3.service.UserService;

@Slf4j
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@GetMapping("/listUsers")
	public String listUsers(Model model,   @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="")  String findName) {

		Page<User> users = userService.findByName(findName, PageRequest.of(page, 10));
			model.addAttribute("users", users);
			model.addAttribute("currentPage", page);
			model.addAttribute("findName", findName);
			return "list-users";

		
	}

	@PostMapping("/listUsers/deleteUser")
	public String deleteUser(Model model, @RequestParam("userEmail") String userEmail, @RequestParam int page, HttpSession session) {
		session.setAttribute("userEmail", userEmail);
		session.setAttribute("page", page);
		return "confirmation-dialog";

	}

	@PostMapping("/confirmAction")
	public String confirmdeleteUser(Model model, @RequestParam("action") String action, HttpSession session) {
		String userEmail = (String) session.getAttribute("userEmail");
		
		if (action.equals("yes")) {
			User user = userService.findOne(userEmail);
			userService.delete(userEmail);
		}
		return "redirect:/listUsers?page="+session.getAttribute("page");

	}

	@PostMapping("/listUsers/changeStatus")
	public String changeUserStatus(Model model, @RequestParam("userEmail") String userEmail, @RequestParam int page, HttpSession session) {
		User user = userService.findOne(userEmail);
		List<Role> roles = roleService.findAll();
		model.addAttribute("user", user);
		model.addAttribute("curRoles", roles);
		session.setAttribute("page", page);
		model.addAttribute("currentPage", page);
		return "/change-user-status";

	}

	@PostMapping("/confirmChangeStatus")
	public String confirmChangeStatus(Model model, @ModelAttribute User user, HttpSession session) {
		User realUser = userService.findOne(user.getEmail());
		if (user.getRoles().size() > 0) {
			realUser.setRoles(null);
			realUser.setRoles(user.getRoles());
		}
		userService.save(realUser); 
		return "redirect:/listUsers?page="+session.getAttribute("page");

	}
	
	@GetMapping("/userData")
	public String changeUserData(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User realUser = userService.findOne(authentication.getName());
		
		model.addAttribute("user", realUser);
		
		return "change-user-data";
	}
	
	@PostMapping("/confirmChangeData")
	public String confirmChangeStatus(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "/change-user-data";
		}
		
		userService.saveChangedData(user);
		return "redirect:/";

	}

}
