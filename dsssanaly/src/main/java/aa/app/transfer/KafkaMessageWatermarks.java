package aa.app.transfer;

import aa.app.input.KafkaMessage;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;

public class KafkaMessageWatermarks  implements AssignerWithPunctuatedWatermarks<KafkaMessage> {

    private static final long serialVersionID = 1L;

    private long currentTimestamp = Long.MIN_VALUE;
    @Nullable
    @Override
    public Watermark checkAndGetNextWatermark(KafkaMessage kafkaMessage, long l) {
        return new Watermark(currentTimestamp == Long.MIN_VALUE ? Long.MIN_VALUE:currentTimestamp -1);
    }

    @Override
    public long extractTimestamp(KafkaMessage kafkaMessage, long l) {
        this.currentTimestamp = kafkaMessage.getTimestamp();
        return kafkaMessage.getTimestamp();
    }
}
