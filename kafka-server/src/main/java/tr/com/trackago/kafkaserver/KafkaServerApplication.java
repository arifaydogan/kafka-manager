package tr.com.trackago.kafkaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@ComponentScan(basePackages = {"tr.com.trackago"})
public class KafkaServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(KafkaServerApplication.class, args);
  }
}
