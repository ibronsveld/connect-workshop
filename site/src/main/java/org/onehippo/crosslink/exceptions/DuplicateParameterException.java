package org.onehippo.crosslink.exceptions;

public class DuplicateParameterException extends DataSourceException {
    private final String key;

    public DuplicateParameterException(String message, String key) {
        super(message);
        this.key = key;
    }
}
