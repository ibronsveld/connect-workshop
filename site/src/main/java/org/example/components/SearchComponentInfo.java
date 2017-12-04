package org.example.components;

import org.hippoecm.hst.core.parameters.Parameter;

public interface SearchComponentInfo {

    @Parameter(name = "searchProvider",
            defaultValue = "search",
            displayName = "Search Provider")
    String getSearchProvider();
}
