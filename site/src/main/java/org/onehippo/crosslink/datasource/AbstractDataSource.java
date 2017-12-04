package org.onehippo.crosslink.datasource;

import org.onehippo.crosslink.exceptions.DataSourceException;
import org.onehippo.crosslink.exceptions.DuplicateParameterException;
import org.onehippo.crosslink.exceptions.ParameterNotFoundException;
import org.onehippo.crosslink.providers.ParameterProvider;
import org.onehippo.crosslink.rest.RestVariable;
import org.springframework.beans.factory.BeanNameAware;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractDataSource implements BeanNameAware, DataSource {
    private String beanName;
    protected final Map<String, ParameterProvider> parameterProviders;
    protected final String resourceName;
    protected final String url;
    protected final String method;

    public AbstractDataSource(Map<String, ParameterProvider> parameterProviders, String resourceName, String url, String method) {
        this.parameterProviders = parameterProviders;
        this.resourceName = resourceName;
        this.url = url;
        this.method = method;
    }

    @Override
    public void setBeanName(String s) {
        this.beanName = s;
    }
    public String getBeanName() {
        return beanName;
    }

    @Override
    public Map<String, Object> getVariables(DataSourceContext context) throws DataSourceException {
        Map<String, Object> parameterValueMap = new HashMap<>();

        Set<Map.Entry<String, ParameterProvider>> entries = parameterProviders.entrySet();
        for (Map.Entry<String, ParameterProvider> providerEntry : entries) {
            String parameterKey = providerEntry.getKey();

            if (parameterValueMap.containsKey(parameterKey)) {
                throw new DuplicateParameterException(parameterKey, "Duplicate parameter");
            }

            ParameterProvider parameterProvider = providerEntry.getValue();
            RestVariable parameterValue = parameterProvider.provide(context);

            if (parameterProvider.isRequired()) {
                if (parameterValue == null || parameterValue.isEmpty())
                    throw new ParameterNotFoundException("Did not find required parameter " + parameterKey);
            }

            // Add it to the list
            parameterValueMap.put(parameterKey, parameterValue);
        }

        return parameterValueMap;
    }
}
