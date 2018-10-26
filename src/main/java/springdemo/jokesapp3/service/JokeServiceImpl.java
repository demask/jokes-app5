package springdemo.jokesapp3.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springdemo.jokesapp3.dao.JokeDao;
import springdemo.jokesapp3.entity.Joke;
import springdemo.jokesapp3.repositories.JokeRepository;

@Service
public class JokeServiceImpl implements JokeService {

	@Autowired 
	private JokeDao jokesDao;
	
	@Autowired 
	private JokeRepository jokesRepository;
	
	@Override
	@Transactional
	public Page<Joke> getJokes(Pageable pageable) {
		
		return jokesDao.getJokes(pageable);
	}

	@Override
	@Transactional
	public void likeJoke(int jokeId) {
		jokesDao.likeJoke(jokeId);

	}

	@Override
	@Transactional
	public void dislikeJoke(int jokeId) {
		jokesDao.dislikeJoke(jokeId);

	}

	@Override
	@Transactional
	public void saveJoke(Joke theJoke) {
		jokesDao.saveJoke(theJoke);

	}
	
	@Override
	public void deleteJoke(Joke joke) {
		jokesRepository.delete(joke);
	}
	@Override
	public Joke getOne(int jokeId) {
		return jokesRepository.getOne(jokeId);
	}

	

}
