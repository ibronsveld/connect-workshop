package org.onehippo.crosslink.providers;

import org.onehippo.crosslink.exceptions.DataSourceException;
import org.onehippo.crosslink.rest.RestVariable;
import org.onehippo.crosslink.rest.RestVariableType;

public abstract class AbstractParameterProvider implements ParameterProvider {

    private final boolean required;
    private final RestVariableType typeToProvide;
    private final boolean encoded;


    public AbstractParameterProvider(RestVariableType typeToProvide, boolean encoded, boolean required) {
        this.required = required;
        this.typeToProvide = typeToProvide;
        this.encoded = encoded;
    }

//    public AbstractParameterProvider(String parameter) {
//        this.typeToProvide = RestVariableType.QUERY;
//        this.required = false;
//        this.encoded = false;
//    }

    protected RestVariable createRestVariable(String value) throws DataSourceException {
        if (value == null) {
            throw new DataSourceException("No value found");
        } else {
            return new RestVariable(value, typeToProvide, encoded);
        }

    }

    @Override
    public boolean isRequired() {
        return required;
    }

    public RestVariableType getTypeToProvide() {
        return typeToProvide;
    }

    public boolean isEncoded() {
        return encoded;
    }
}
