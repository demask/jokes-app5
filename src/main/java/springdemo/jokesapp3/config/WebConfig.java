package springdemo.jokesapp3.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAspectJAutoProxy
public class WebConfig implements WebMvcConfigurer {

	

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/");
	}

	

}
