package com.ihm.kafkatraining.partition;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;



@Component
@EnableBinding(Sink.class)
@Slf4j
public class KafkaPartitionConsumer {

    @StreamListener(Sink.INPUT)
    public void listen(@Payload String in, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, @Header("partitionKey") String key, @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment) throws InterruptedException {
        log.info("{} received from partition  {} with key {} acknowledgment {}", in, partition, key, acknowledgment);
        //simulate a slow process
        try {
            Thread.sleep(4000);
            log.info("Processed {} received from partition  {} with key {}", in, partition, key);
            acknowledge(acknowledgment);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void acknowledge(Acknowledgment acknowledgment) {
        acknowledgment.acknowledge();
    }


}
