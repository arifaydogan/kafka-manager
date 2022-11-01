package tr.com.trackago.kafkaserver.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import tr.com.trackago.kafkaserver.common.enums.TopicEnum;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "kafkamanager", name = "producer", havingValue = "true")
public class TopicProducer {


    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topicName, String key, String message) {
        log.info("Send Kafka Key-Message : {}" + key + ":" + message);
        Object object = message;
        kafkaTemplate.send(topicName, key, object);
    }

    public void sendMessage(String topicName, String key, Object message) {

        TopicEnum tpc = TopicEnum.getTopic(topicName);

        ListenableFuture<SendResult<String, Object>> future = this.kafkaTemplate.send(tpc.getTopic(), tpc.getPrefix()+key, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("Sent message: " + message + " with offset: " + result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message : " + message, ex);
            }
        });
    }



}