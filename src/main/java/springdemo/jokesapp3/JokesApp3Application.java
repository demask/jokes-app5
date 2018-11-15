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
    		  
    		  User newAdmin1 = new User("robert@mail.com", "Robert", "123456");
    		  userService.createAdmin(newAdmin1); 
    		  
    		  User newUser1 = new User("cindy@mail.com", "Cindy", "123456");
    		  userService.createUser(newUser1); 
    		  
    		  User newAdmin2 = new User("art@mail.com", "Art", "123456");
    		  userService.createAdmin(newAdmin2); 
    		  
    		  User newUser2 = new User("andrew@mail.com", "Andrew", "123456");
    		  userService.createUser(newUser2); 
    		   
    		  User newUser4 = new User("tami@mail.com", "Tami", "123456");
    		  userService.createUser(newUser4); 
    		  
    		  User newUser6 = new User("richard@mail.com", "Richard", "123456");
    		  userService.createUser(newUser6); 
    		    
    		  User newUser8 = new User("robin@mail.com", "Robin", "123456");
    		  userService.createUser(newUser8); 
    		  
    		  User newUser12 = new User("tina@mail.com", "Tina", "123456");
    		  userService.createUser(newUser12); 
    		  
    		  User newUser13 = new User("gail@mail.com", "Gail", "123456");
    		  userService.createUser(newUser13); 
    		  
    		  User newUser14 = new User("john@mail.com", "John", "123456");
    		  userService.createUser(newUser14); 
    		  
    		  User newUser15= new User("sharon@mail.com", "Sharon", "123456");
    		  userService.createUser(newUser15); 
    		  
    		  User newUser16 = new User("michael@mail.com", "Michael", "123456");
    		  userService.createUser(newUser16);
    	  }
		  
		  
		  
	}
}
