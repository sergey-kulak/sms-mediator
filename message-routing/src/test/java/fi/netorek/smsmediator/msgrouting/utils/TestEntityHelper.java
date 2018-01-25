package fi.netorek.smsmediator.msgrouting.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import fi.netorek.smsmediator.proto.InboundMessage;

public class TestEntityHelper {

    public static InboundMessage.Message buildInboundMessage() {
        return buildInboundMessage(RandomStringUtils.randomAlphabetic(10));
    }

    public static InboundMessage.Message buildInboundMessage(String body) {
        return InboundMessage.Message.newBuilder()
                .setPhoneNumber(RandomStringUtils.random(10))
                .setBody(body)
                .setOrigin(RandomStringUtils.randomAlphabetic(5))
                .build();
    }

    public static Message<?> buildMessage(Object payload) {
        return MessageBuilder.withPayload(payload).build();
    }
}
