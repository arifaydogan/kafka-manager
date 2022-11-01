package tr.com.trackago.paymentconsumer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import tr.com.trackago.paymentconsumer.service.PaymentService;
import tr.com.trackago.paymentconsumer.service.impl.PaymentServiceImpl;

@Slf4j
@Component
public class PaymentConfig {

  @Bean(name = "consumerService")
  public PaymentService paymentService() {
    return new PaymentServiceImpl();
  }

}
