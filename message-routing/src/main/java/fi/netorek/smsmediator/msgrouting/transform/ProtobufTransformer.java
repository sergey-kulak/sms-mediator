package fi.netorek.smsmediator.msgrouting.transform;

import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.google.protobuf.InvalidProtocolBufferException;
import fi.netorek.smsmediator.common.exception.ProtobufParseException;
import fi.netorek.smsmediator.proto.InboundMessage;

public class ProtobufTransformer implements Transformer {

    /**
     * Transform a message with payload serialized with Google Protobuf to InboundMessage.Message
     *
     * @param rawMessage Message with raw byte[] serialized with Google Protobuf
     * @return Message with InboundMessage.Message payload
     * @throws ProtobufParseException if deserialization goes wrong
     */
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
