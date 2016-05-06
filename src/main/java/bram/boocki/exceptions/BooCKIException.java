package bram.boocki.exceptions;

public class BooCKIException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BooCKIException(String message) {
		super(message);
	}
	
	public BooCKIException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
