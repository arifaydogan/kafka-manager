package tr.com.trackago.transferconsumer.service;

import java.math.BigDecimal;
import tr.com.trackago.kafkabase.service.KafkaBaseService;
import tr.com.trackago.transferconsumer.dto.TransferInfoDto;

public interface TransferService extends KafkaBaseService {

   TransferInfoDto transfer(String fromIBAN, String toIBAN, BigDecimal amount);





}