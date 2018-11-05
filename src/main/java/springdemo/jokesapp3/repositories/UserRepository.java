package springdemo.jokesapp3.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import springdemo.jokesapp3.entity.User;

public interface UserRepository  extends JpaRepository<User, String> {

	List<User> findByNameLike(String name); 
	
	List<User> findByNameLikeIgnoreCase(String name);

}
