package springdemo.jokesapp3.resterror;

public class JokesNotFoundException extends RuntimeException{

	public JokesNotFoundException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public JokesNotFoundException(String message) {
		super(message);
		
	}

	public JokesNotFoundException(Throwable cause) {
		super(cause);
		
	}

	
}
