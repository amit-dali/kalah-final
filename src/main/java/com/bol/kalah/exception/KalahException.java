package com.bol.kalah.exception;

/**
 * @author AMDALI
 *
 */
public class KalahException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public KalahException(String message) {
	super(message);
    }

    public KalahException(String errorMessage, Throwable err) {
	super(errorMessage, err);
    }
}
