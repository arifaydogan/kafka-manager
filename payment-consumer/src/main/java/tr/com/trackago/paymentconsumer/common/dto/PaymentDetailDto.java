package tr.com.trackago.paymentconsumer.common.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PaymentDetailDto {

    private String txId;
    private BigDecimal price;
    private String resultCode;

}
