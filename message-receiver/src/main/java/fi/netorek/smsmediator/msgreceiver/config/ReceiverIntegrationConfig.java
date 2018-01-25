package fi.netorek.smsmediator.msgreceiver.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.http.Http;
import org.springframework.util.MultiValueMap;

import fi.netorek.smsmediator.common.utils.ProtobufUtils;
import fi.netorek.smsmediator.proto.InboundMessage;

@Configuration
@IntegrationComponentScan
@PropertySource("classpath:/config/msg-receiver.properties")
public class ReceiverIntegrationConfig {
    @Value("${msg-receiver.outbound-exchange}")
    private String outboundExchangeName;

    @Bean
    public IntegrationFlow httpInboundGatewayFlow(AmqpTemplate amqpTemplate) {
        return IntegrationFlows.from(Http.inboundGateway("/inbound/receiver")
                .requestMapping(r -> r
                        .methods(HttpMethod.GET)
                        .params("phonenumber", "body", "origin"))
                .payloadExpression("#requestParams"))
                .handle((payload, headers) -> {
                    MultiValueMap<String, String> params = (MultiValueMap<String, String>) payload;

                    return ProtobufUtils.getBytes(InboundMessage.Message.newBuilder().
                            setPhoneNumber(params.get("phonenumber").get(0)).
                            setBody(params.get("body").get(0)).
                            setOrigin(params.get("origin").get(0))
                            .build());
                })
                .handle(Amqp.outboundAdapter(amqpTemplate).exchangeName(outboundExchangeName).get())
                .get();
    }

}

