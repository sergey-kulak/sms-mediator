package fi.netorek.smsmediator.msgrouting.transform;

import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class ProtobufTransformer implements Transformer {
    public Message<?> transform(Message<?> message) {
        System.out.println("proto");
        return MessageBuilder.withPayload(new String((byte[]) message.getPayload()))
                .copyHeaders(message.getHeaders())
                .build();
    }
}
