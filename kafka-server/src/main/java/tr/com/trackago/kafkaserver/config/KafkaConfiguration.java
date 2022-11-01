package tr.com.trackago.kafkaserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;

@Configuration
public class KafkaConfiguration {


    @Bean
    public SeekToCurrentErrorHandler errorHandler(DeadLetterPublishingRecoverer deadLetterPublishingRecoverer) {
        return new SeekToCurrentErrorHandler(deadLetterPublishingRecoverer);
    }


    @Bean
    public DeadLetterPublishingRecoverer publisher(KafkaTemplate bytesTemplate) {
        return new DeadLetterPublishingRecoverer(bytesTemplate);
    }
}
