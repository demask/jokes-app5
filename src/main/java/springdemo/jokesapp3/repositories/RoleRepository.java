package springdemo.jokesapp3.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import springdemo.jokesapp3.entity.Role;

public interface RoleRepository  extends JpaRepository<Role, String>{
 
}
