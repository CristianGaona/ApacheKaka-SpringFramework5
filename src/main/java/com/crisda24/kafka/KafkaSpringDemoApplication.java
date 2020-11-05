package com.crisda24.kafka;

import java.util.List;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.kafka.config.KafkaListenerEndpointRegistry;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;


@SpringBootApplication 
public class KafkaSpringDemoApplication implements CommandLineRunner {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private KafkaListenerEndpointRegistry registry;
	
	private static final Logger log = LoggerFactory.getLogger(KafkaSpringDemoApplication.class); 
	@KafkaListener(id= "autoStartup", autoStartup = "false", topics = "crisda24-topic", containerFactory = "listenerContainerFactory", groupId = "crisda24-group",
			properties = {"max.poll.interval.ms:4000", "max.poll.records:10"})
	public void listen(List<ConsumerRecord<String, String>> messages) {
		log.info("Start reading messages");
		for (ConsumerRecord<String, String> message : messages) {
			log.info("Parttition {}, Offset {}, Key {}, Value {} ", message.partition(), message.offset(), message.key(), message.value());
		}
		log.info("Batch complete");
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringDemoApplication.class, args);
	}
	
	@Scheduled (fixedDelay = 1000, initialDelay = 500)
	public void print() {
		log.info("Crisda24 rocks!");
	}

	@Override
	public void run(String...args) throws Exception {
		
		/*for (int i = 0; i < 100; i++) {
			kafkaTemplate.send("crisda24-topic", String.valueOf(i), String.format("Sample message %d ", i));
			
		}*/
		/*log.info("Waiting to start");
		Thread.sleep(5000);
		log.info("Starting");
		registry.getListenerContainer("autoStartup").start();
		Thread.sleep(5000);
		registry.getListenerContainer("autoStartup").stop();*/
		/*ListenableFuture<SendResult<String, String>> future =  kafkaTemplate.send("crisda24-topic","Sample message" );
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
		});*/
	}
	
}
