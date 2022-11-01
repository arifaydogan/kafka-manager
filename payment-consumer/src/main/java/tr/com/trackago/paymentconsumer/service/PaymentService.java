package tr.com.trackago.paymentconsumer.service;

import java.math.BigDecimal;
import tr.com.trackago.kafkabase.service.KafkaBaseService;
import tr.com.trackago.paymentconsumer.common.dto.PaymentDetailDto;

public interface PaymentService extends KafkaBaseService {

   PaymentDetailDto pay(String orderName, Long userId, Long productId, BigDecimal price);
}
