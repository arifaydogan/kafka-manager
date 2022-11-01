package tr.com.trackago.kafkaserver.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseMessageRequest<T> {

    private String topicName;
    private String key;
    private T message;

}
