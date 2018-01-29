package fi.netorek.smsmediator.msgreceiver.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.http.Http;
import org.springframework.integration.mapping.OutboundMessageMapper;
import org.springframework.util.MultiValueMap;

import fi.netorek.smsmediator.common.utils.ProtobufUtils;
import fi.netorek.smsmediator.proto.InboundMessage;

@Configuration
@IntegrationComponentScan
public class ReceiverIntegrationConfig {
    private static final String PARAM_PHONE_NUBER = "phonenumber";
    private static final String PARAM_BODY = "body";
    private static final String PARAM_ORIGIN = "origin";

    @Value("${msg-receiver.outbound-exchange}")
    private String outboundExchangeName;

    @Bean
    public OutboundMessageMapper<HttpEntity<String>> staticStatusOutboundMessageMapper(){
        return message -> new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Bean
    public IntegrationFlow httpInboundGatewayFlow(AmqpTemplate amqpTemplate) {
        return IntegrationFlows.from(Http.inboundChannelAdapter("/inbound/receiver")
                .replyTimeout(0)
                .replyMapper(staticStatusOutboundMessageMapper())
                .requestMapping(r -> r
                        .methods(HttpMethod.GET)
                        .params(PARAM_PHONE_NUBER, PARAM_BODY, PARAM_ORIGIN))
                .payloadExpression("#requestParams"))
                .handle((payload, headers) -> {
                    MultiValueMap<String, String> params = (MultiValueMap<String, String>) payload;

                    return ProtobufUtils.getBytes(InboundMessage.Message.newBuilder()
                            .setPhoneNumber(params.get(PARAM_PHONE_NUBER).get(0))
                            .setBody(params.get(PARAM_BODY).get(0))
                            .setOrigin(params.get(PARAM_ORIGIN).get(0))
                            .build());
                })
                .handle(Amqp.outboundAdapter(amqpTemplate).exchangeName(outboundExchangeName).get())
                .get();
    }

}

