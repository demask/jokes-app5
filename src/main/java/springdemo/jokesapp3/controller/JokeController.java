package springdemo.jokesapp3.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;
import springdemo.jokesapp3.dao.JokeDao;
import springdemo.jokesapp3.entity.Category;
import springdemo.jokesapp3.entity.Joke;
import springdemo.jokesapp3.repositories.JokeRepository;
import springdemo.jokesapp3.service.JokeService;
import springdemo.jokesapp3.entity.JokeHelp;


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
	public String listJokes(Model theModel,  @RequestParam(defaultValue="0") int page) {
		Page<Joke> theJokes = jokeService.getJokes(PageRequest.of(page, 10));
		theModel.addAttribute("jokes", theJokes);
		theModel.addAttribute("currentPage", page);
		return "list-jokes";
	}

	@GetMapping("/likeJoke")
	public String likeJoke(Model model, @RequestParam("jokeId") int jokeId, @RequestParam int page) {
		jokeService.likeJoke(jokeId);
		return "redirect:/?page="+page;
	}

	@GetMapping("/dislikeJoke")
	public String dislikeJoke(@RequestParam("jokeId") int jokeId,  @RequestParam int page) {
		jokeService.dislikeJoke(jokeId);
		return "redirect:/?page="+page;
	}

	@GetMapping("/new")
	public String showJokeForm(Model theModel) {
		jokeHelp.setClear();
		theModel.addAttribute("joke", jokeHelp);
		return "new";
	}

	@PostMapping("/saveJoke")
	public String saveJoke(@Valid @ModelAttribute("joke") JokeHelp theJokeHelp, 
			BindingResult theBindingResult, Model theModel) {
		
		if (theBindingResult.hasErrors()) {
			return "new";
		} else {
			Joke joke = new Joke(theJokeHelp.getContent());
			joke.setCategory(new Category(theJokeHelp.getCategory()));
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
