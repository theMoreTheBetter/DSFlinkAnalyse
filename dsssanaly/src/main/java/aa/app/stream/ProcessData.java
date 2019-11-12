package aa.app.stream;

import aa.app.input.KafkaMessage;
import aa.app.transfer.KafkaMessageSchema;
import aa.app.transfer.KafkaMessageWatermarks;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

public class ProcessData {
    public static void main(String[] args) {
        final ParameterTool paramterTool = org.apache.flink.api.java.utils.ParameterTool.fromArgs(args);
        if(paramterTool.getNumberOfParameters() < 5){
            return;
        }

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().disableSysoutLogging();
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4,10000));
        env.enableCheckpointing(5000);
        env.getConfig().setGlobalJobParameters(paramterTool);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        FlinkKafkaConsumer011 flinkKafkaConsumer = new FlinkKafkaConsumer011<KafkaMessage>(paramterTool.getRequired("input-topic"), new KafkaMessageSchema(), paramterTool.getProperties());
        flinkKafkaConsumer.assignTimestampsAndWatermarks(new KafkaMessageWatermarks());

    }
}
