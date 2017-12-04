package org.onehippo.crosslink.providers;

import org.onehippo.crosslink.datasource.DataSourceContext;
import org.onehippo.crosslink.exceptions.DataSourceException;
import org.onehippo.crosslink.rest.RestVariable;
import org.onehippo.crosslink.rest.RestVariableType;

import java.util.Map;
import java.util.Set;

public class QueryStringParameterProvider extends AbstractParameterProvider {
    private final String parameter;

    public QueryStringParameterProvider(String parameter, RestVariableType typeToProvide, boolean encoded, boolean required) {
        super(typeToProvide, encoded, required);
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }

    @Override
    public RestVariable provide(DataSourceContext context) throws DataSourceException {

        Map<String, String[]> parameterMap = context.getRequest().getParameterMap();

        // Check if we can find it
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            if (entry.getKey().equals(this.getParameter())) {
                // TODO: Fix multiple
                String[] value = entry.getValue();
                if (value != null && value.length > 0) {
                    return createRestVariable(value[0]);
                }
            }
        }

        return null;
    }
}
