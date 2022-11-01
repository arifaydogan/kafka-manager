package tr.com.trackago.kafkaserver.consumer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tr.com.trackago.kafkabase.service.KafkaBaseService;
import tr.com.trackago.kafkaserver.common.dto.TopicInfo;

@Slf4j
@Service
@ConditionalOnProperty(prefix = "kafkamanager", name = "consumer", havingValue = "true")
public class TopicConsumer {

    @Qualifier("consumerService")
    @Autowired
    KafkaBaseService kafkaBaseService;

    @Value("${topic.name.consumer}")
    private String topicName;

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapAdress;

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ConsumerRecord<String, Object> payload) {
        log.info("Topic: {}", topicName);
        log.info("Consuming Message: {}", payload.value());
        kafkaBaseService.consume(payload.value());
    }

    public TopicInfo getConsumersNames() throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAdress);

        AdminClient adminClient = AdminClient.create(properties);

        ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
        listTopicsOptions.listInternal(true);
        String topics = adminClient.listTopics(listTopicsOptions).names().get().toString();
        log.info(topics);
        return TopicInfo.builder()
                .topics(topics)
                .build();
    }


}