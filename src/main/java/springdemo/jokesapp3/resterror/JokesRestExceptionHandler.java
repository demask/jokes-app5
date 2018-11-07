package springdemo.jokesapp3.resterror;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class JokesRestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<JokesErrorResponse> handleException(JokesNotFoundException e){
		
		JokesErrorResponse errorResponse = new JokesErrorResponse();
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setMessage(e.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
}
