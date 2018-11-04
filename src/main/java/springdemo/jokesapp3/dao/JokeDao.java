package springdemo.jokesapp3.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import springdemo.jokesapp3.entity.Joke;



public interface JokeDao {
	
	public Page<Joke> getJokes(Pageable pageable);

	public void likeJoke(int jokeId);

	public void dislikeJoke(int jokeId);

	public void saveJoke(Joke joke);

}
