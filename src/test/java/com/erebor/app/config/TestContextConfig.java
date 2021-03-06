package com.erebor.app.config;

import com.erebor.app.kafka.AccountOpenedPublisher;
import com.erebor.app.kafka.AccountOpenedSubscriber;
import com.erebor.domain.repository.AccountRepository;
import com.erebor.infrastructure.persistence.cassandra.repository.AccountCassandraRepo;
import com.erebor.infrastructure.repository.AccountRepositoryInCassandra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestContextConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestContextConfig.class);

	@Bean
	AccountRepository bindAccountRepository(AccountCassandraRepo accountCassandraRepo) {
		LOGGER.info("Binding account repository to test context");
		return new AccountRepositoryInCassandra(accountCassandraRepo);
	}

	@Bean
	public AccountOpenedSubscriber bindAccountOpenedSubscriber() {
		return new AccountOpenedSubscriber();
	}

	@Bean
	public AccountOpenedPublisher bindAccountOpenedPublisher() {
		return new AccountOpenedPublisher();
	}

}
