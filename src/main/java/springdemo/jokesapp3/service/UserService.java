package springdemo.jokesapp3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	@PersistenceContext
	private EntityManager entityManager;

	public void createUser(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		Role userRole = new Role("USER");
		List<Role> roles = new ArrayList<>();
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);

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

	public void saveChangedData(User user) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User realUser = findOne(authentication.getName());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setRoles(realUser.getRoles());
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public Page<User> findByName(String name, Pageable pageable) {
		
		long numberOfSearchedUsers = userRepository.countByNameIgnoreCaseContaining(name);
		long numberOfUsers = userRepository.count();
		TypedQuery<User> userquery;
		userquery = entityManager.createQuery("from User where lower(name) like '%" + name + "%'", User.class)
				.setFirstResult((pageable.getPageNumber() - 1) * 10).setMaxResults(10);

		List<User> users = userquery.getResultList();
		Page<User> page;

		if (numberOfSearchedUsers < numberOfUsers) {
			page = new PageImpl<User>(users, PageRequest.of(pageable.getPageNumber() - 1, 10), numberOfSearchedUsers);
		} else {
			page = new PageImpl<User>(users, PageRequest.of(pageable.getPageNumber() - 1, 10), numberOfUsers);
		}
		
		return page;

	}

	public boolean isUserPresent(String email) {
		Optional<User> u = userRepository.findById(email);
		if (u.isPresent())
			return true;

		return false;
	}
	


}
