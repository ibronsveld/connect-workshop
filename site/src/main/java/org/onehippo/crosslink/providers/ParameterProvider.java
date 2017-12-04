package org.onehippo.crosslink.providers;

import org.onehippo.crosslink.datasource.DataSourceContext;
import org.onehippo.crosslink.exceptions.DataSourceException;
import org.onehippo.crosslink.rest.RestVariable;

public interface ParameterProvider {
    RestVariable provide(DataSourceContext context) throws DataSourceException;
    boolean isRequired();
}
