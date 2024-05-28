package com.behsacorp.processmanagement.pm.exception;

public class PMException extends Exception {

    private Exception wrappedException;

    public PMException() {
    }

    public PMException(String message, Exception wrappedException) {
        super(message);
        this.wrappedException = wrappedException;
    }

    public PMException(Exception wrappedException) {
        super();
        this.wrappedException = wrappedException;
    }

    public PMException(String message) {
        super(message);
    }
}
