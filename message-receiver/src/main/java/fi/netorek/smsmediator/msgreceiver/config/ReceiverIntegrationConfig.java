package fi.netorek.smsmediator.msgreceiver.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.expression.spel.standard.SpelExpressionParser;
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

        String phonePropName = propertyResolver().getProperty(PropertyResolver.Property.PHONE);
        String bodyPropName = propertyResolver().getProperty(PropertyResolver.Property.BODY);
        String originPropName = propertyResolver().getProperty(PropertyResolver.Property.ORIGIN);

        return IntegrationFlows.from(Http.inboundGateway("/inbound/receiver")
                .replyTimeout(0)
                .statusCodeExpression(new SpelExpressionParser().parseExpression("200"))
                .requestMapping(r -> r
                        .methods(HttpMethod.GET)
                        .params(phonePropName, bodyPropName, originPropName))
                .payloadExpression("#requestParams"))
                .handle((payload, headers) -> {
                    MultiValueMap<String, String> params = (MultiValueMap<String, String>) payload;

                    return ProtobufUtils.getBytes(InboundMessage.Message.newBuilder()
                            .setPhoneNumber(params.get(phonePropName).get(0))
                            .setBody(params.get(bodyPropName).get(0))
                            .setOrigin(params.get(originPropName).get(0))
                            .build());
                })
                .handle(Amqp.outboundAdapter(amqpTemplate).exchangeName(outboundExchangeName).get())
                .get();
    }

    @Bean
    public PropertyResolver propertyResolver() {
        return new PropertyResolver();
    }

}

