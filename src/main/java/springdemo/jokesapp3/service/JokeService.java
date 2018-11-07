package springdemo.jokesapp3.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import springdemo.jokesapp3.entity.Joke;

public interface JokeService {

	public Page<Joke> getJokes(Pageable pageable);

	public void likeJoke(int jokeId);

	public void dislikeJoke(int jokeId);

	public void saveJoke(Joke theJoke);
	
	public void deleteJoke(Joke joke);
	
	public Joke getOne(int jokeId); 
	
	public List<Joke> getJokes();

	public List<Joke> getJokesByCategory(String category);
}
