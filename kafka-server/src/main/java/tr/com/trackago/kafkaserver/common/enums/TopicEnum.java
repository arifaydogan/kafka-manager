package tr.com.trackago.kafkaserver.common.enums;
import java.util.Arrays;

public enum TopicEnum {

    OTHER(TopicNames.OTHER, "oth_"),
    PAYMENT(TopicNames.PAYMENT, "pay_"),
    TRANSFER(TopicNames.TRANSFER, "tra_");


    private String topic;
    private String prefix;

    public static TopicEnum getTopic(String topic) {
        return Arrays.stream(TopicEnum.values())
                .filter(te -> te.name().equalsIgnoreCase(topic))
                .findAny()
                .orElse(OTHER);
    }

    public static String getTopicName(String topic){
        return getTopic(topic).topic;
    }

    TopicEnum(String topic, String prefix) {
        this.topic = topic;
        this.prefix = prefix;
    }

    public String getTopic() {
        return topic;
    }

    public String getPrefix() {
        return prefix;
    }


    /**
     * Constants of topic names in Kafka
     * */
    public static class TopicNames {
        public static final String PAYMENT = "payment";
        public static final String TRANSFER = "transfer";
        public static final String OTHER = "other";

    }

}
