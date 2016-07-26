package com.shaunmccready.exception;

/**
 * Main exception class which is used to create the ResponseDTO whenever there is an error
 *
 */
public class EventException extends Exception {

    private static final long serialVersionUID = 1L;

    private String errorCode;

    private String message;


    public EventException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public EventException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
