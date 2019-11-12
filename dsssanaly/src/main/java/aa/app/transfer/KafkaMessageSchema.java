package aa.app.transfer;

import aa.app.input.KafkaMessage;
import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class KafkaMessageSchema implements DeserializationSchema<KafkaMessage>, SerializationSchema<KafkaMessage> {

    private static final long serialVersionUID = 1L;
    @Override
    public byte[] serialize(KafkaMessage kafkaMessage) {
        String jsonstring = JSON.toJSONString(kafkaMessage);
        return jsonstring.getBytes();
    }
    @Override
    public KafkaMessage deserialize(byte[] bytes) throws IOException {
        KafkaMessage km = JSON.parseObject(new String(bytes),KafkaMessage.class);
        return km;
    }
    @Override
    public boolean isEndOfStream(KafkaMessage kafkaMessage) {
        return false;
    }

    @Override
    public TypeInformation<KafkaMessage> getProducedType() {
        return  TypeInformation.of(KafkaMessage.class);
    }
}
