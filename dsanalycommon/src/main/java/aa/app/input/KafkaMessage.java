package aa.app.input;

public class KafkaMessage {

    private String jsonMessage;
    private Long timestamp;
    private int count;

    public String getJsonMessage() {
        return jsonMessage;
    }

    public void setJsonMessage(String jsonMessage) {
        this.jsonMessage = jsonMessage;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "KafkaMessage{" +
                "jsonMessage='" + jsonMessage + '\'' +
                ", timestamp=" + timestamp +
                ", count=" + count +
                '}';
    }
}
