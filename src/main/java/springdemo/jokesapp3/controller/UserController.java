package springdemo.jokesapp3.controller;

import java.util.List;


import javax.servlet.http.HttpSession;
import javax.sound.midi.MidiDevice.Info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String listUsers(Model model) {
		List<User> theUsers = userService.findAll();
		model.addAttribute("users", theUsers);
		return "list-users";
	}

	@PostMapping("/deleteUser")
	public String deleteUser(Model model, @RequestParam("userEmail") String userEmail, HttpSession session) {
		session.setAttribute("userEmail", userEmail);
		return "confirmation-dialog";

	}

	@PostMapping("/confirmAction")
	public String confirmdeleteUser(Model model, @RequestParam("action") String action, HttpSession session) {
		String userEmail = (String) session.getAttribute("userEmail");
		
		if (action.equals("yes")) {
			User user = userService.findOne(userEmail);
			userService.delete(userEmail);
		}
		return "redirect:/listUsers";

	}

	@PostMapping("/changeStatus")
	public String changeUserStatus(Model model, @RequestParam("userEmail") String userEmail) {
		User user = userService.findOne(userEmail);
		List<Role> roles = roleService.findAll();
		model.addAttribute("user", user);
		model.addAttribute("curRoles", roles);
		return "/change-user-status";

	}

	@PostMapping("/confirmChangeStatus")
	public String confirmChangeStatus(Model model, @ModelAttribute User user) {
		User realUser = userService.findOne(user.getEmail());
		if (user.getRoles().size() > 0) {
			realUser.setRoles(null);
			realUser.setRoles(user.getRoles());
		}
		userService.save(realUser);
		return "redirect:/listUsers";

	}

}
