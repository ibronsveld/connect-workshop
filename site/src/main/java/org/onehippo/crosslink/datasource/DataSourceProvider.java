package org.onehippo.crosslink.datasource;

import org.onehippo.crosslink.exceptions.DataSourceNotFoundException;

import java.util.List;
import java.util.Map;

public class DataSourceProvider {

    private List<DataSource> dataSourceList;

    public DataSourceProvider(List<DataSource> dataSourceList) {
        this.dataSourceList = dataSourceList;
    }

    public DataSource getDataSource(String name) throws DataSourceNotFoundException {
        for (DataSource dataSource : dataSourceList) {
            if (dataSource instanceof AbstractDataSource) {
                if (((AbstractDataSource)dataSource).getBeanName().equals(name)) {
                    return dataSource;
                }
            }
        }

        throw new DataSourceNotFoundException("Data Source with " + name + " not found");
    }
}
