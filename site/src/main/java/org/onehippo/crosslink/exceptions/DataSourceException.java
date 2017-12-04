package org.onehippo.crosslink.exceptions;

public class DataSourceException extends Exception {
    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(Throwable cause) {
        super(cause);
    }
}
