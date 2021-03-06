package springdemo.jokesapp3.dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import springdemo.jokesapp3.entity.Category;
import springdemo.jokesapp3.entity.Joke;

@Slf4j
@Repository
public class JokeDAOImpl implements JokeDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Joke> getJokes(Pageable pageable) {
		
		TypedQuery<Joke> jokequery = entityManager.createQuery("from Joke order by ((likes-dislikes), likes) desc", Joke.class);
		
		List<Joke> jokes = jokequery.getResultList();
		List<Joke> jokes2; 
		if(((pageable.getPageNumber()+1)*pageable.getPageSize()) >= jokes.size()) {
			
			 jokes2 = jokes.subList((pageable.getPageNumber()*pageable.getPageSize()), jokes.size());
		}
		else {	
			 jokes2 = jokes.subList(pageable.getPageNumber()*pageable.getPageSize(), 
					 ((pageable.getPageNumber()+1)*pageable.getPageSize()));
		}
		
		Page<Joke> page = new PageImpl<Joke>(jokes2, pageable, jokes.size());
			
		return page;
	}

	@Override
	public void likeJoke(int jokeId) {
		Joke joke = entityManager.getReference(Joke.class, jokeId);
		int likes = joke.getLikes();
		likes++;
		joke.setLikes(likes);
	}

	@Override
	public void dislikeJoke(int jokeId) {
		Joke joke = entityManager.getReference(Joke.class, jokeId);
		int dislikes = joke.getDislikes();
		dislikes++;
		joke.setDislikes(dislikes);

	}

	@Override
	public void saveJoke(Joke joke) {
		TypedQuery<Category> query = entityManager.
				createQuery("from Category where lower(name) like '%" + joke.getCategory().getName() + "%'",
				Category.class);

		List<Category> listcategory = query.getResultList();
		Category category;
		if (listcategory.size() != 0) {
			category = listcategory.get(0);
		} else {
			category = new Category();
			category.setName(joke.getCategory().getName());
			entityManager.persist(category);
		}
		category.addJoke(joke);
		entityManager.persist(joke);
	}

	@Override
	public List<Joke> getJokesByCategory(String category) {
		TypedQuery<Category> categoryQuery  = entityManager.createQuery("from Category where lower(name) like '%" + category + "%'", Category.class);
		List<Category> categoryList = categoryQuery.getResultList();
		Category cat;
		
		if (categoryList.size() == 0) {
			return null;
		} 
		cat = categoryList.get(0);
		TypedQuery<Joke> jokequery = entityManager.createQuery("from Joke where category_id=" + cat.getId(), Joke.class);
		List<Joke> jokes = jokequery.getResultList();
		
		return jokes;
		
	}
}



















