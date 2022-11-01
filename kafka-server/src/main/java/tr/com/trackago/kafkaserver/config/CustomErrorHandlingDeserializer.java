package tr.com.trackago.kafkaserver.config;

import java.util.Map;
import java.util.function.Function;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.serializer.FailedDeserializationInfo;
import org.springframework.kafka.support.serializer.SerializationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;


@Component
public class CustomErrorHandlingDeserializer<T> implements Deserializer<T> {


    public static final String KEY_FUNCTION = "spring.deserializer.key.function";
    public static final String VALUE_FUNCTION = "spring.deserializer.value.function";
    public static final String KEY_DESERIALIZER_CLASS = "spring.deserializer.key.delegate.class";
    public static final String VALUE_DESERIALIZER_CLASS = "spring.deserializer.value.delegate.class";
    private static final Logger LOG = LoggerFactory.getLogger(CustomErrorHandlingDeserializer.class);
    private Deserializer<T> delegate;
    private boolean isForKey;
    private Function<FailedDeserializationInfo, T> failedDeserializationFunction;

    public CustomErrorHandlingDeserializer() {
    }

    public CustomErrorHandlingDeserializer(Deserializer<T> delegate) {
        this.delegate = this.setupDelegate(delegate);
    }

    public void setFailedDeserializationFunction(Function<FailedDeserializationInfo, T> failedDeserializationFunction) {
        this.failedDeserializationFunction = failedDeserializationFunction;
    }

    public boolean isForKey() {
        return this.isForKey;
    }

    public void setForKey(boolean isKey) {
        this.isForKey = isKey;
    }

    public CustomErrorHandlingDeserializer<T> keyDeserializer(boolean isKey) {
        this.isForKey = isKey;
        return this;
    }

    public void configure(Map<String, ?> configs, boolean isKey) {
        if (this.delegate == null) {
            this.setupDelegate(configs, isKey ? "spring.deserializer.key.delegate.class" : "spring.deserializer.value.delegate.class");
        }

        Assert.state(this.delegate != null, "No delegate deserializer configured");
        this.delegate.configure(configs, isKey);
        this.isForKey = isKey;
        if (this.failedDeserializationFunction == null) {
            this.setupFunction(configs, isKey ? "spring.deserializer.key.function" : "spring.deserializer.value.function");
        }

    }

    public void setupDelegate(Map<String, ?> configs, String configKey) {
        if (configs.containsKey(configKey)) {
            try {
                Object value = configs.get(configKey);
                Class<?> clazz = value instanceof Class ? (Class) value : ClassUtils.forName((String) value, null);
                this.delegate = this.setupDelegate(clazz.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

    }

    private Deserializer<T> setupDelegate(Object delegate) {
        Assert.isInstanceOf(Deserializer.class, delegate, "'delegate' must be a 'Deserializer', not a ");
        return (Deserializer) delegate;
    }

    private void setupFunction(Map<String, ?> configs, String configKey) {
        if (configs.containsKey(configKey)) {
            try {
                Object value = configs.get(configKey);
                Class<?> clazz = value instanceof Class ? (Class) value : ClassUtils.forName((String) value, null);
                Assert.isTrue(Function.class.isAssignableFrom(clazz), "'function' must be a 'Function ', not a " + clazz.getName());
                this.failedDeserializationFunction = (Function) clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

    }

    public T deserialize(String topic, byte[] data) {
        try {
            return this.delegate.deserialize(topic, data);
        } catch (Exception e) {
            return this.recoverFromSupplier(topic, null, data, e);
        }
    }

    public T deserialize(String topic, Headers headers, byte[] data) {
        try {
            if (this.isForKey) {
                headers.remove("springDeserializerExceptionKey");
            } else {
                headers.remove("springDeserializerExceptionValue");
            }

            return this.delegate.deserialize(topic, headers, data);
        } catch (Exception e) {
            SerializationUtils.deserializationException(headers, data, e, this.isForKey);
            return this.recoverFromSupplier(topic, headers, data, e);
        }
    }

    private T recoverFromSupplier(String topic, Headers headers, byte[] data, Exception exception) {
        if (this.failedDeserializationFunction != null) {
            FailedDeserializationInfo failedDeserializationInfo = new FailedDeserializationInfo(topic, headers, data, this.isForKey, exception);
            return this.failedDeserializationFunction.apply(failedDeserializationInfo);
        } else {
            return null;
        }
    }

    public void close() {
        if (this.delegate != null) {
            this.delegate.close();
        }

    }
}