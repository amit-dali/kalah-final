package com.bol.kalah.exception;

import org.springframework.http.HttpStatus;

/**
 * @author AMDALI
 *
 */
public class ExceptionResponse {

    private final String message;
    private final long timestamp;
    private final HttpStatus status;

    public ExceptionResponse(String message, HttpStatus status) {
	this.status = status;
	this.message = message;
	this.timestamp = System.currentTimeMillis();
    }

    public static ExceptionResponse of(final String message, HttpStatus status) {
	return new ExceptionResponse(message, status);
    }

    public String getMessage() {
	return message;
    }

    public long getTimestamp() {
	return timestamp;
    }

    public HttpStatus getStatus() {
	return status;
    }

}
