package org.onehippo.crosslink.datasource;

import org.onehippo.cms7.crisp.api.broker.ResourceServiceBroker;
import org.onehippo.cms7.crisp.api.resource.Resource;
import org.onehippo.cms7.crisp.api.resource.ResourceException;
import org.onehippo.cms7.services.HippoServiceRegistry;
import org.onehippo.crosslink.exceptions.DataSourceException;
import org.onehippo.crosslink.exceptions.DuplicateParameterException;
import org.onehippo.crosslink.providers.ParameterProvider;
import org.onehippo.crosslink.rest.RestVariable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GenericDataSource extends AbstractDataSource {

    public GenericDataSource(Map<String, ParameterProvider> parameterProviders, String resourceName, String url, String method) {
        super(parameterProviders, resourceName, url, method);
    }

    public Resource execute(DataSourceContext context) throws DataSourceException {
        Map<String, Object> variables = getVariables(context);
        return execute(context, variables);
    }

    @Override
    public Resource execute(DataSourceContext context, Map<String, Object> variables) throws DataSourceException {

        Resource resource = null;

        try {
            ResourceServiceBroker resourceServiceBroker = HippoServiceRegistry.getService(ResourceServiceBroker.class);
            resource = resourceServiceBroker.findResources(resourceName, url, variables);
        } catch (ResourceException ex) {
            throw new DataSourceException(ex);
        }
        return resource;
    }
}
