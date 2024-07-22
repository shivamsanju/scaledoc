package com.shvmsnju.scaledoc_project_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.shvmsnju.scaledoc_project_service.dto.AssetIngestionDTO;

@Service
public class IngestionProducerService {

    private Logger LOG = LoggerFactory.getLogger(IngestionProducerService.class);

    @Autowired
    private KafkaTemplate<String, AssetIngestionDTO> kafkaTemplate;

    @Value("${kafka.ingestion.topic.name}")
    private String ingestionTopic;

    public void sendAssetForIngestion(AssetIngestionDTO assetIngestionDTO) {
        LOG.info(String.format("Sending assetIngestionDTO=%s", assetIngestionDTO.getAssetId()));
        kafkaTemplate.send(ingestionTopic, assetIngestionDTO);
    }

}
