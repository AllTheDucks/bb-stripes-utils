package com.alltheducks.bb.stripes.exceptions;

/**
 * Created by wiley on 1/10/15.
 */
public class EntitlementException extends Exception {

    public EntitlementException() {
        super();
    }

    public EntitlementException(String message) {
        super(message);
    }

    public EntitlementException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntitlementException(Throwable cause) {
        super(cause);
    }

    protected EntitlementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
