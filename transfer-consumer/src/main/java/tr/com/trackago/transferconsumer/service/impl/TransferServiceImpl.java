package tr.com.trackago.transferconsumer.service.impl;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.trackago.kafkabase.service.KafkaBaseServiceImpl;
import tr.com.trackago.transferconsumer.dto.TransferInfoDto;
import tr.com.trackago.transferconsumer.service.TransferService;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransferServiceImpl extends KafkaBaseServiceImpl implements TransferService {


  @Override
  public void consume(Object... values) {
    Object[] objects = values;
    transfer(objects[0].toString(),objects[1].toString() ,new BigDecimal(objects[3].toString()));
  }

  @Override
  public TransferInfoDto transfer(String fromIBAN, String toIBAN, BigDecimal amount) {
    return TransferInfoDto.builder()
        .txId(UUID.randomUUID().toString())
        .resultCode("200")
        .build();
  }
}
