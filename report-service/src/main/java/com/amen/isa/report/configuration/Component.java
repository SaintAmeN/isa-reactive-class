package com.amen.isa.report.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@ComponentScan(basePackages = {"com.amen.isa.model.mapper", "com.amen.isa.component",})
@EnableReactiveMongoRepositories(basePackages = {"com.amen.isa.component"})
public class Component {
}
