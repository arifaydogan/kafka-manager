package tr.com.trackago.transferconsumer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import tr.com.trackago.transferconsumer.service.TransferService;
import tr.com.trackago.transferconsumer.service.impl.TransferServiceImpl;

@Slf4j
@Component
public class TransferConfig {

  @Bean(name = "consumerService")
  public TransferService paymentService() {
    return new TransferServiceImpl();
  }

}
