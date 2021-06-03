package com.api.algafood.domain.exception;

public class StorageException extends RuntimeException{

	public StorageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}

	private static final long serialVersionUID = 1L;

}
