package aa.app.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${kafka.consumer.servers}")
    private String servers;

    @Value("${kafka.consumer.enable.auto.commit}")
    private boolean enableAutoCommit;

    @Value("${kafka.consumer.session.timeout}")
    private int sessionTimeout;

    @Value("${kafka.consumer.auto.commit.interval}")
    private String autoCommitInterval;

    @Value("${kafka.comsumer.auto.offset.reset}")
    private String autoOffsetReset;

    @Value("${kafka.consumer.concurrency}")
    private int concurrency;

    @Value("${kafka.consumer.group.id}")
    private String groupId;


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,String>> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,String> fatory = new ConcurrentKafkaListenerContainerFactory<>();
        fatory.setConsumerFactory(consumerFactory());
        fatory.setConcurrency(concurrency);
        fatory.getContainerProperties().setPollTimeout(1500);
        return fatory;
    }

    private ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    private Map<String,Object> consumerConfigs() {
        Map<String,Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,servers);
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,enableAutoCommit);
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,autoOffsetReset);
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,autoCommitInterval);
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,sessionTimeout);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        return propsMap;
    }

    @Bean
    public Listener listener() {return new Listener();}
}
