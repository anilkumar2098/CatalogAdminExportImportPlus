package com.boutiqaat.catalogadminexportimportplus.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@EnableKafka
@PropertySource("classpath:application.yml")
@Configuration
@Service
public class FileExportConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileExportConsumer.class);

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

   // final BinlogProcessorService batchProcessor;

    @KafkaListener(topics = "catalog_product_grid", groupId = "group_id")
    public void consume(ExportEvent exportEvent){
        LOGGER.info(String.format("Export details received -> %s", exportEvent));

    }


   /* @Bean
    public java.util.function.Consumer<KStream<String, ExportEvent>> process() {
        // @formatter:off
        return input -> input.filter((key, value) -> Objects.nonNull(key))
                .filter((k,v)-> !shouldNotProcess(v))
                .foreach((key, value) -> batchProcessor.process(value));
        // @formatter:on
    }*/

   /* private boolean shouldNotProcess(ExportEvent v) {
        return BinlogTypes.DELETE.getType().equals(v.getExportType())
                || BinlogTypes.BOOTSTRAP_START.getType().equals(v.getExportType())
                || BinlogTypes.BOOTSTRAP_COMPLETE.getType().equals(v.getExportType());
    }*/
}
