package tr.com.trackago.kafkaserver.common.dto;

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
public class PaymentRequest {

    private String orderName;
    private Long userId;
    private Long productId;
    private BigDecimal price;

}
