package springdemo.jokesapp3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import springdemo.jokesapp3.entity.Joke;

public interface JokeRepository extends JpaRepository<Joke, Integer>, JokeRepositoryCustom{
	
}
