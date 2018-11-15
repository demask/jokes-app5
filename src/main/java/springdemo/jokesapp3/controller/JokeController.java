package springdemo.jokesapp3.controller;


import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;


import lombok.extern.slf4j.Slf4j;

import springdemo.jokesapp3.entity.Category;
import springdemo.jokesapp3.entity.Joke;

import springdemo.jokesapp3.service.JokeService;
import springdemo.jokesapp3.entity.JokeHelp;
import springdemo.jokesapp3.entity.Pager;


@Slf4j
@Controller
public class JokeController {

	@Autowired
	private JokeService jokeService;
	
	@Autowired
	private JokeHelp jokeHelp;	
	
//	add an initbinder.. to convert trim input strings
//	remove leading and trailing whitespace
//	resolve issue for validation
	@InitBinder
	public void InitBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@GetMapping("/")
	public String listJokes(Model model,  @RequestParam(defaultValue="1") int page) {

		Page<Joke> jokes = jokeService.getJokes(PageRequest.of(page, 10));
		Pager pager = new Pager(jokes.getTotalPages(), jokes.getNumber(), 5);
		model.addAttribute("jokes", jokes);
		model.addAttribute("currentPage", page);
		model.addAttribute("pager", pager);
		
		return "list-jokes";
	}

	@GetMapping("/likeJoke")
	public String likeJoke(@RequestParam("jokeId") int jokeId, @RequestParam int page) {
		jokeService.likeJoke(jokeId);
		return "redirect:/?page="+page;
	}

	@GetMapping("/dislikeJoke")
	public String dislikeJoke(@RequestParam("jokeId") int jokeId,  @RequestParam int page) {
		jokeService.dislikeJoke(jokeId);
		return "redirect:/?page="+page;
	}

	@GetMapping("/new")
	public String showJokeForm(Model model) {
		jokeHelp.setClear();
		model.addAttribute("joke", jokeHelp);
		return "new";
	}

	@PostMapping("/saveJoke")
	public String saveJoke(@Valid @ModelAttribute("joke") JokeHelp jokeHelp, 
			BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "new";
		} else {
			Joke joke = new Joke(jokeHelp.getContent());
			joke.setCategory(new Category(jokeHelp.getCategory()));
			jokeService.saveJoke(joke);
			return "redirect:/";
		}

	}
	
	@GetMapping("/deleteJoke")
	public String deleteJoke(@RequestParam("jokeId") int jokeId, @RequestParam int page) {
		
		jokeService.deleteJoke(jokeService.getOne(jokeId));
		return "redirect:/?page="+page;
	}

}
