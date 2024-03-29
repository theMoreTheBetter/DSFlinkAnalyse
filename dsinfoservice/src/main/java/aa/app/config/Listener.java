package aa.app.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class Listener {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics={"test1"})
    public void listen(ConsumerRecord<?,?> record){
        logger.info("kafka的key" + record.key());
        logger.info("kafka的value" + record.value().toString());
    }
}
