package springdemo.jokesapp3.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springdemo.jokesapp3.entity.Joke;
import springdemo.jokesapp3.resterror.JokesNotFoundException;
import springdemo.jokesapp3.service.JokeService;

@RestController
@RequestMapping("/api")
public class JokeRestController {

	@Autowired
	private JokeService jokeService;
	
	@GetMapping("/jokes")
	public List<Joke> getJokes(){
		
		return jokeService.getJokes();
	}
	
	@GetMapping("/jokes/{category}")
	public List<Joke> getJokesByCategory(@PathVariable String category){
		List<Joke> jokes = jokeService.getJokesByCategory(category);
		if(jokes == null) {
			throw new JokesNotFoundException("Jokes category not found - " + category);
		}
		return jokes;
	}
	
	
}
