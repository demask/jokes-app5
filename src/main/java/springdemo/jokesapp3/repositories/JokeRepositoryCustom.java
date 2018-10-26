package springdemo.jokesapp3.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import springdemo.jokesapp3.entity.Joke;


//interface nije implementiran u ovom slucaju
public interface JokeRepositoryCustom {
	
    public Page<Joke> findAll(Pageable pageable);
}
