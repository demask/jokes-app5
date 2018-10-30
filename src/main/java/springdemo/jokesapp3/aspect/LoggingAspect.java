package springdemo.jokesapp3.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import springdemo.jokesapp3.entity.Joke;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

	@AfterReturning(pointcut = "execution(* springdemo.jokesapp3.dao.JokeDAOImpl.getJokes(..))", returning = "result")
	public void getJokesAdvice(JoinPoint joinPoint, Page<Joke> result) {

//		print out which method we are advising on
		String method = joinPoint.getSignature().toShortString();
		log.info("Executing @AfterReturning on getJokes() " + method);

//		print out the results of the method call
		log.info("Result is " + result);

	}

	@Before("execution(* springdemo.jokesapp3.dao.JokeDAOImpl.likeJoke(..))")
	public void beforelikeJokeAdvice(JoinPoint joinPoint) {

		String method = joinPoint.getSignature().toShortString();
		log.info("Executing @Before on likeJoke() " + method);

		Object[] args = joinPoint.getArgs();

		for (Object tempArg : args) {
			log.info("Joke ID = " + tempArg.toString());

		}
	}
	
	@Before("execution(* springdemo.jokesapp3.dao.JokeDAOImpl.dislikeJoke(..))")
	public void beforedislikeJokeAdvice(JoinPoint joinPoint) {

		String method = joinPoint.getSignature().toShortString();
		log.info("Executing @Before on dislikeJoke() " + method);

		Object[] args = joinPoint.getArgs();

		for (Object tempArg : args) {
			log.info("Joke ID = " + tempArg.toString());

		}
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	