package springdemo.jokesapp3.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import springdemo.jokesapp3.entity.Joke;

//klasa nije implementirana u ovom slucaju
@Slf4j
public class JokeRepositoryImpl implements JokeRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManger;
	
	@Override
	@Transactional
	public Page<Joke> findAll(Pageable pageable) {
		log.info("in jokes repositoryimpl");
		TypedQuery<Joke> theJokequery = entityManger.createQuery("from jokes order by ((likes-dislikes), likes) desc", Joke.class);

		List<Joke> jokes = theJokequery.getResultList();
		List<Joke> jokes2; 
		if(((pageable.getPageNumber()+1)*pageable.getPageSize())-1 > jokes.size()) {
			 jokes2 = jokes.subList((pageable.getPageNumber()*pageable.getPageSize()), jokes.size());
		}
		else {
			 jokes2 = jokes.subList(pageable.getPageNumber()*pageable.getPageSize(), ((pageable.getPageNumber()+1)*pageable.getPageSize()));
		}
		
		Page<Joke> page = new PageImpl<Joke>(jokes2, pageable, jokes.size());
			
		return page;
	}

}
