package aa.app;

public class KafkaEvent {

    private String word;
    private int frequency;
    private long timestamp;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public KafkaEvent() {
    }

    public KafkaEvent(String word, int frequency, long timestamp) {
        this.word = word;
        this.frequency = frequency;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "KafkaEvent{" +
                "word='" + word + '\'' +
                ", frequency=" + frequency +
                ", timestamp=" + timestamp +
                '}';
    }

    public static KafkaEvent fromString(String eventStr){
        String[] split = eventStr.split(",");
        return new KafkaEvent(split[0],Integer.valueOf(split[1]),Long.valueOf(split[2]));


    }
}
