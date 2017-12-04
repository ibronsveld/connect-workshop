package org.onehippo.crosslink.datasource;

import org.onehippo.cms7.crisp.api.resource.Resource;
import org.onehippo.crosslink.exceptions.DataSourceException;
import org.onehippo.crosslink.rest.RestVariable;

import java.util.Map;

public interface DataSource {
    Resource execute(DataSourceContext context) throws DataSourceException;
    Resource execute(DataSourceContext context, Map<String, Object> variables) throws DataSourceException;

    Map<String, Object> getVariables(DataSourceContext context) throws DataSourceException;
}
