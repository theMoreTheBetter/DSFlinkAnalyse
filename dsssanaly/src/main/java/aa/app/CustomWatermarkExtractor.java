package aa.app;

import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;

public class CustomWatermarkExtractor implements AssignerWithPunctuatedWatermarks<KafkaEvent> {

    private static final long serialVersionID = 1L;

    private long currentTimestamp = Long.MIN_VALUE;

    @Nullable
    @Override
    public Watermark checkAndGetNextWatermark(KafkaEvent kafkaEvent, long l) {
        return new Watermark(currentTimestamp == Long.MIN_VALUE ? Long.MIN_VALUE:currentTimestamp -1);
    }

    @Override
    public long extractTimestamp(KafkaEvent kafkaEvent, long l) {
        this.currentTimestamp = kafkaEvent.getTimestamp();
        return kafkaEvent.getTimestamp();
    }
}
