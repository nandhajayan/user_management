package com.example.usermanagement.config.multitenant;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.usermanagement.model.DataSourceConfig;


public interface DataSourceConfigRepository extends JpaRepository<DataSourceConfig, Long> {
    DataSourceConfig findByName(String name);
}