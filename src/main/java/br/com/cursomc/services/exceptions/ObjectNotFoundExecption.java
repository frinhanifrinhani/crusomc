package br.com.cursomc.services.exceptions;

public class ObjectNotFoundExecption extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundExecption(String msg) {
		super(msg);
	}
	
	public ObjectNotFoundExecption(String msg, Throwable cause) {
		
	}
}
