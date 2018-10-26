package springdemo.jokesapp3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springdemo.jokesapp3.entity.User;
import springdemo.jokesapp3.service.UserService;

@SpringBootApplication
public class JokesApp3Application implements CommandLineRunner {

	@Autowired
	 private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(JokesApp3Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		  {
    		  User newAdmin = new User("admin@mail.com", "Admin", "123456");
    		  userService.createAdmin(newAdmin); 
    		  
    		  User newUser = new User("user@mail.com", "User", "123456");
    		  userService.createUser(newUser); 
    	  }
	}
}
