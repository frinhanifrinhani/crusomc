package br.com.cursomc.services.exceptions;

public class DataIntegrityExecption extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DataIntegrityExecption(String msg) {
		super(msg);
	}
	
	public DataIntegrityExecption(String msg, Throwable cause) {
		
	}
}
