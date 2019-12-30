package com.ihm.kafkatraining.partition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Random;


@Component
@EnableBinding(Source.class)
@Slf4j
public class KafkaPartitionProducer {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

           private static final String[] data = new String[] {
                   "0K5209", "0SHKDT", "1D238G",
                   "21QP3I", "25ETTF", "2PKROO",
                   "3TCPGE", "423DIZ", "4IO037",
                   "4LTJAL", "6HNXE4", "6RBJIC",
           };


           /**
            * This method generates random data and places it on the stream at specific partitions.
            * @return
            */
           @InboundChannelAdapter(channel = Source.OUTPUT, poller = @Poller(fixedRate = "1000"))
           public Message<?> generate() {
               int point = RANDOM.nextInt(data.length);
               String value = data[point];
               log.info("Sending: {}", value);
               return MessageBuilder.withPayload(value)
                       .setHeader(KafkaHeaders.PARTITION_ID, point)
                       .setHeader("partitionKey", value) //we would set the partition key as our customer ID and it used the algorithm  key.hashCode() % partitionCount) to decide what partition to send it to
                       .build();
           }
}
