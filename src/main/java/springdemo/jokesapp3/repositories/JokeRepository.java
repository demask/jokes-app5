package springdemo.jokesapp3.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import springdemo.jokesapp3.entity.Joke;

public interface JokeRepository extends JpaRepository<Joke, Integer>, JokeRepositoryCustom{
	
}
