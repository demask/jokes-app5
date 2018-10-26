package springdemo.jokesapp3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import springdemo.jokesapp3.entity.Role;
import springdemo.jokesapp3.entity.User;
import springdemo.jokesapp3.repositories.UserRepository;

@Slf4j
@Service
public class UserService {
 
	@Autowired
	private UserRepository userRepository;

	public void createUser(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		Role userRole = new Role("USER");
		List<Role> roles = new ArrayList<>();
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);
		log.info("in createUser()");

	}

	public void createAdmin(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		Role userRole = new Role("ADMIN");
		List<Role> roles = new ArrayList<>();
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);
	}

	public User findOne(String email) {

		return userRepository.findById(email).get();
	}

	public List<User> findAll() {

		return userRepository.findAll();
	}

	public Page<User> findAll(Pageable pageable) {

		return userRepository.findAll(pageable);
	}

	public void delete(String email) {
		
		userRepository.deleteById(email);
	}

	public void save(User user) {

		userRepository.save(user);
	}

	
//	trebalo bi biti implementirano u search baru za korisnike
	public List<User> findByName(String name) {

		return userRepository.findByNameLike("%" + name + "%");
	}

	public boolean isUserPresent(String email) {
		Optional<User> u = userRepository.findById(email);
		if (u.isPresent())
			return true;

		return false;
	}

}
