package com.shvmsnju.scaledoc_rag_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

import com.shvmsnju.scaledoc_rag_service.dto.AssetIngestionDTO;

@SpringBootApplication
@EnableKafka
public class Application {

	private static final String INGESTION_TOPIC = "${kafka.ingestion.topic.name}";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@KafkaListener(topics = INGESTION_TOPIC)
	public void processMessage(@Payload AssetIngestionDTO assetIngestionDTO) {
		System.out.println("Received message: " + assetIngestionDTO);
	}

}
