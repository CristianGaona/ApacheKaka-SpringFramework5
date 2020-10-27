package com.crisda24.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class KafkaSpringDemoApplication {

	private static final Logger log = LoggerFactory.getLogger(KafkaSpringDemoApplication.class); 
	@KafkaListener(topics = "crisda24-topic", groupId = "crisda24-group")
	public void listen(String message) {
		log.info("Message Recived {} ", message);
	}
	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringDemoApplication.class, args);
	}

}
