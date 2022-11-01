package tr.com.trackago.kafkaserver.controller;

import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tr.com.trackago.kafkaserver.common.dto.BaseMessageRequest;
import tr.com.trackago.kafkaserver.common.dto.PaymentRequest;
import tr.com.trackago.kafkaserver.common.dto.TopicInfo;
import tr.com.trackago.kafkaserver.consumer.TopicConsumer;
import tr.com.trackago.kafkaserver.producer.TopicProducer;

@RestController(value = "/kafka")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "kafkamanager", name = "producer", havingValue = "true")
public class KafkaController {


    private final TopicProducer topicProducer;
    private final TopicConsumer topicConsumer;

    @RequestMapping(path = "/getTopics", method = RequestMethod.GET)
    public TopicInfo getTopicInfo() throws ExecutionException, InterruptedException {
        return topicConsumer.getConsumersNames();
    }


    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public void test() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", "Example message " + (int) (Math.random() * 1000));
        topicProducer.sendMessage("test", "test-" + (int) (Math.random() * 1000), jsonObj);
    }

    @RequestMapping(path = "/sendMessage", method = RequestMethod.POST)
    public void sendMessage(@RequestBody BaseMessageRequest<PaymentRequest> request) {
        topicProducer.sendMessage(request.getTopicName(), request.getKey(), request.getMessage());
    }
}
