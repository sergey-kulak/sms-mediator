package fi.netorek.smsmediator.msgrouting.transform;

import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.google.protobuf.InvalidProtocolBufferException;
import fi.netorek.smsmediator.common.exception.ProtobufParseException;
import fi.netorek.smsmediator.proto.InboundMessage;

public class ProtobufTransformer implements Transformer {
    public Message<?> transform(Message<?> rawMessage) {
        try {
            byte[] rawPayload = (byte[]) rawMessage.getPayload();
            InboundMessage.Message message = InboundMessage.Message.parseFrom(rawPayload);
            
            return MessageBuilder.withPayload(message)
                    .copyHeaders(rawMessage.getHeaders())
                    .build();
        } catch (InvalidProtocolBufferException e) {
            throw new ProtobufParseException(e);
        }
    }
}
