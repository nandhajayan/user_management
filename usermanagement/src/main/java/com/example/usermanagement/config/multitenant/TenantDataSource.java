package com.example.usermanagement.config.multitenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;

import com.example.usermanagement.model.DataSourceConfig;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TenantDataSource implements Serializable {

	private HashMap<String, DataSource> dataSources = new HashMap<>();

	@Autowired
	private DataSourceConfigRepository configRepo;

	public DataSource getDataSource(String name) {
		if (dataSources.get(name) != null) {
			return dataSources.get(name);
		}
		DataSource dataSource = createDataSource(name);
		if (dataSource != null) {
			dataSources.put(name, dataSource);
		}
		return dataSource;
	}

	public Map<String, DataSource> getAll() {
		List<DataSourceConfig> configList = configRepo.findAll();
		Map<String, DataSource> result = new HashMap<>();
		for (DataSourceConfig config : configList) {
			DataSource dataSource = getDataSource(config.getName());
			result.put(config.getName(), dataSource);
		}
		return result;
	}
    private DataSource createDataSource(String name) {
        DataSourceConfig config = configRepo.findByName(name);
        if (config != null) {
            DataSourceBuilder factory = DataSourceBuilder
                    .create().driverClassName(config.getDriverClassName())
                    .username(config.getUsername())
                    .password(config.getPassword())
                    .url(config.getUrl());
            DataSource ds = factory.build();
            if (config.isInitialize()) {
                initialize(ds);
            }
            return ds;
        }
        return null;
    }

    private void initialize(DataSource dataSource) {
     //initialize with schema
    }
}