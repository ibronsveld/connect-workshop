package org.onehippo.crosslink.providers;

import org.onehippo.crosslink.datasource.DataSourceContext;
import org.onehippo.crosslink.exceptions.DataSourceException;
import org.onehippo.crosslink.rest.RestVariable;
import org.onehippo.crosslink.rest.RestVariableType;

public class SiteMapItemParameterProvider extends AbstractParameterProvider {

    private final String parameter;

    public SiteMapItemParameterProvider(String parameter, RestVariableType typeToProvide, boolean encoded, boolean required) {
        super(typeToProvide, encoded, required);
        this.parameter = parameter;
    }

    @Override
    public RestVariable provide(DataSourceContext context) throws DataSourceException {
        String parameter = context.getRequest().getRequestContext().getResolvedSiteMapItem().getParameter(this.parameter);
        return createRestVariable(parameter);
    }
}
