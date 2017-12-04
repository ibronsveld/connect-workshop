package org.onehippo.crosslink.rest;

import com.google.common.base.Strings;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RestVariable {

    private String value;
    private RestVariableType type;
    private boolean encoded;

    public RestVariable(String value, RestVariableType type, boolean encoded) {
        this.value = value;
        this.type = type;
        this.encoded = encoded;
    }

    public String getValue() {
        return value;
    }
    public void setValue(final String value) {
        this.value = value;
    }

    public RestVariableType getType() {
        return type;
    }

    public boolean isEncoded() {
        return encoded;
    }

    @Override
    public String toString() {
        if (!encoded)
            return value;
        else
            return encodeValue(value);
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    protected String encodeValue(final String v) {
        if (!Strings.isNullOrEmpty(v)) {
            final Base64.Encoder encoder = Base64.getEncoder();
            final byte[] encoded = encoder.encode(v.getBytes());
            return new String(encoded, StandardCharsets.UTF_8);
        }
        return v;
    }
}
