package org.example.components;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.crisp.api.broker.ResourceServiceBroker;
import org.onehippo.cms7.crisp.api.resource.Resource;
import org.onehippo.cms7.services.HippoServiceRegistry;
import org.onehippo.crosslink.datasource.DataSource;
import org.onehippo.crosslink.datasource.DataSourceContext;
import org.onehippo.crosslink.datasource.DataSourceProvider;
import org.onehippo.crosslink.exceptions.DataSourceException;
import org.onehippo.crosslink.exceptions.DataSourceNotFoundException;
import org.onehippo.crosslink.exceptions.ParameterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service("org.example.components.SearchComponent")
@ParametersInfo(type = SearchComponentInfo.class)
public class SearchComponent extends BaseHstComponent {

    @Autowired
    @Qualifier("dataSourceProvider")
    private DataSourceProvider dataSourceProvider;

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);

        SearchComponentInfo componentInfo = this.getComponentParametersInfo(request);
        String searchType = componentInfo.getSearchProvider();
        request.setAttribute("searchType", searchType);

        DataSourceContext dataSourceContext = DataSourceContext.create(request, response, this);

        try {
            DataSource searchProvider = dataSourceProvider.getDataSource(searchType);
            Map<String, Object> variables = searchProvider.getVariables(dataSourceContext);
            Resource moviesResource = searchProvider.execute(dataSourceContext,variables);

            request.setAttribute("variables", variables);
            request.setAttribute("results", moviesResource);
        } catch (DataSourceNotFoundException e) {
            e.printStackTrace();
        } catch (ParameterNotFoundException e) {
            // Ignore, we have no data
        } catch (DataSourceException e) {
            e.printStackTrace();
        }
    }
}

