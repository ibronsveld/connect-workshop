package org.onehippo.crosslink.providers;

import org.onehippo.crosslink.datasource.DataSourceContext;
import org.onehippo.crosslink.exceptions.DataSourceException;
import org.onehippo.crosslink.rest.RestVariable;
import org.onehippo.crosslink.rest.RestVariableType;

import java.util.Map;
import java.util.Set;

public class ConstantParameterProvider extends AbstractParameterProvider {
    private final String value;

    public ConstantParameterProvider(RestVariableType typeToProvide, boolean encoded, boolean required, String value) {
        super(typeToProvide, encoded, required);
        this.value = value;
    }

    @Override
    public RestVariable provide(DataSourceContext context) throws DataSourceException {
        return createRestVariable(value);
    }
}
