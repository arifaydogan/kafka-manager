package tr.com.trackago.kafkabase.service;


public abstract class KafkaBaseServiceImpl extends BaseExternalServiceImpl implements KafkaBaseService {

  public abstract void consume(Object... values);
}
