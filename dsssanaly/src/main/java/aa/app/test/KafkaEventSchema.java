package aa.app.test;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;


import java.io.IOException;

public class KafkaEventSchema implements DeserializationSchema<KafkaEvent>, SerializationSchema<KafkaEvent> {

    private static final long serialVersionUID = 1L;

    @Override
    public byte[] serialize(KafkaEvent kafkaEvent) {
        return kafkaEvent.toString().getBytes();
    }

    public KafkaEvent deserialize(byte[] message) throws IOException{
        return KafkaEvent.fromString(new String(message));
    }

    public boolean isEndOfStream(KafkaEvent nextElement){return false;}

    @Override
    public TypeInformation<KafkaEvent> getProducedType() {
        return TypeInformation.of(KafkaEvent.class);
    }
}
