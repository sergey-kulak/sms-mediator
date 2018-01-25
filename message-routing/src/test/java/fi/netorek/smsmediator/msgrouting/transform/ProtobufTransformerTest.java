package fi.netorek.smsmediator.msgrouting.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import fi.netorek.smsmediator.common.utils.ProtobufUtils;
import fi.netorek.smsmediator.proto.InboundMessage;

public class ProtobufTransformerTest {

    @Test
    public void testTransform() {
        InboundMessage.Message dataMessage = InboundMessage.Message.newBuilder()
                .setPhoneNumber("ph1")
                .setBody("test body")
                .setOrigin("origin").build();

        Message message = MessageBuilder.withPayload(ProtobufUtils.getBytes(dataMessage)).build();

        Message result = new ProtobufTransformer().transform(message);
        assertNotNull(result);
        InboundMessage.Message resultMessage = (InboundMessage.Message) result.getPayload();
        assertEquals(dataMessage, resultMessage);
    }

}