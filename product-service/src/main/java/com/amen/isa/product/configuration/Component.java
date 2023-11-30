package com.amen.isa.product.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"com.amen.isa.component.configuration", "com.amen.isa.component", "com.amen.isa.model.mapper", "com.amen.isa.component.controller"})
@EnableJpaRepositories(basePackages = {"com.amen.isa.component"})
@EntityScan(basePackages = "com.amen.isa.model.domain")
public class Component {
}
