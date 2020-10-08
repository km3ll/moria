package com.bankless.app.kafka;

import com.bankless.app.kafka.dto.KafkaAccountOpenedEvent;
import com.bankless.app.kafka.dto.KafkaAccountOpenedValidator;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

public class AccountOpenedPublisher {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountOpenedPublisher.class);

	@Value("${kafka.topic.account.opened}")
	private String topic;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void publish(KafkaAccountOpenedEvent event) {

		String key = UUID.randomUUID().toString();
		String kafkaEvent = event.toCsv();

		LOGGER.info(String.format("Publishing event: topic '%s' key '%s' data %s", topic, key, kafkaEvent));

		ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, key, kafkaEvent);
		result.addCallback(new PublisherLoggingCallBack());

	}

}