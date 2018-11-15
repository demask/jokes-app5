package springdemo.jokesapp3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import springdemo.jokesapp3.entity.Role;
import springdemo.jokesapp3.repositories.RoleRepository;

@Slf4j
@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	
	public List<Role> findAll(){
		return  roleRepository.findAll();
	}
}
