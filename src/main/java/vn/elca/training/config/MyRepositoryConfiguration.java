package vn.elca.training.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = { "vn.elca.training.dom" })
@EnableJpaRepositories(basePackages = { "vn.elca.training.dao" })
@EnableTransactionManagement
public class MyRepositoryConfiguration {

}
