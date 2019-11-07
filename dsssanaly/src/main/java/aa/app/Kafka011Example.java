package aa.app;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

public class Kafka011Example {
    public static void main(String[] args) throws Exception {
        args = new String[]{"--input-topic","test3",
                            "--output-topic","test4",
                            "--bootstrap.servers","localhost:9092",
                            "--zookeeper.connect","localhost:2181",
                            "--group.id","myconsumer1"};
        final ParameterTool paramterTool = ParameterTool.fromArgs(args);
        if(paramterTool.getNumberOfParameters() < 5){
            return;
        }

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().disableSysoutLogging();
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4,10000));
        env.enableCheckpointing(5000);
        env.getConfig().setGlobalJobParameters(paramterTool);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<KafkaEvent> input = env.addSource(new FlinkKafkaConsumer011<KafkaEvent>(
                paramterTool.getRequired("input-topic"),new KafkaEventSchema(),paramterTool.getProperties()
                ).assignTimestampsAndWatermarks(new CustomWatermarkExtractor()))
                .keyBy("word")
                .map(new RollingAdditionMapper());

        input.addSink(new FlinkKafkaProducer011<KafkaEvent>(
                paramterTool.getRequired("output-topic"),
                new KafkaEventSchema(),
                paramterTool.getProperties()
        ));

        env.execute("Kafka 0.11 Example");

    }

    private static class RollingAdditionMapper extends RichMapFunction<KafkaEvent,KafkaEvent> {
        private static final long serialVersionUID = 1L;

        private transient ValueState<Integer> currentTotalCount;
        @Override
        public KafkaEvent map(KafkaEvent kafkaEvent) throws Exception {
            Integer totalCount = currentTotalCount.value();
            if(totalCount == null){
                totalCount = 0;
            }
            totalCount += kafkaEvent.getFrequency();
            currentTotalCount.update(totalCount);
            return new KafkaEvent(kafkaEvent.getWord(),totalCount, kafkaEvent.getTimestamp());
        }

        @Override
        public void open(Configuration parameters) throws Exception {
            currentTotalCount = getRuntimeContext().getState(new ValueStateDescriptor<Integer>("currentTotalCount",Integer.class));
        }
    }
}
