package tr.com.trackago.paymentconsumer.service.impl;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.trackago.kafkabase.service.KafkaBaseServiceImpl;
import tr.com.trackago.paymentconsumer.common.dto.PaymentDetailDto;
import tr.com.trackago.paymentconsumer.service.PaymentService;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl extends KafkaBaseServiceImpl implements PaymentService {


  @Override
  public void consume(Object... values) {
    Object[] objects = values;
    pay(objects[0].toString(),new Long(objects[1].toString()) ,new Long(objects[2].toString()) ,new BigDecimal(objects[3].toString()));
  }

  @Override
  public PaymentDetailDto pay(String orderName, Long userId, Long productId, BigDecimal price) {
    return PaymentDetailDto.builder()
        .txId(UUID.randomUUID().toString())
        .price(price)
        .resultCode("200")
        .build();
  }
}
