package org.onehippo.crosslink.datasource;

import org.hippoecm.hst.core.component.HstComponent;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

public class DataSourceContext {

    private final HstRequest request;
    private final HstResponse response;
    private final HstComponent component;

    protected DataSourceContext(HstRequest request, HstResponse response, HstComponent component) {
        this.request = request;
        this.response = response;
        this.component = component;
    }

    public HstRequest getRequest() {
        return request;
    }

    public HstResponse getResponse() {
        return response;
    }

    public HstComponent getComponent() {
        return component;
    }

    public static DataSourceContext create(HstRequest request, HstResponse response, HstComponent component) {
        return new DataSourceContext(request, response, component);
    }
}
