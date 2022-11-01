package tr.com.trackago.kafkabase.service;

public interface KafkaBaseService extends BaseExternalService {

  void consume(Object... values);
}
