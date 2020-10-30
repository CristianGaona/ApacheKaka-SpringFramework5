package com.crisda24.kafka;

import java.awt.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

@SpringBootApplication
public class KafkaSpringDemoApplication implements CommandLineRunner {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(KafkaSpringDemoApplication.class); 
	@KafkaListener(topics = "crisda24-topic", groupId = "crisda24-group")
	public void listen(String message) {
		log.info("Message Recived {} ", message);
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringDemoApplication.class, args);
	}

	@Override
	public void run(String...args) throws Exception {
	
		ListenableFuture<SendResult<String, String>> future =  kafkaTemplate.send("crisda24-topic","Sample message" );
	    future.addCallback(new KafkaSendCallback<String, String>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				log.info("Message send ", result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("Error sending message ", ex);
				
			}

			@Override
			public void onFailure(KafkaProducerException ex) {
				log.error("Error sending message ", ex);
				
			}
		});
	}
	
}
