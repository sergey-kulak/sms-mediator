package fi.netorek.smsmediator.msgrouting.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;

import fi.netorek.smsmediator.msgrouting.eip.ProtobufTransformer;
import fi.netorek.smsmediator.msgrouting.eip.RouteKeyTransformer;
import fi.netorek.smsmediator.msgrouting.eip.SimpleSmsTextParser;
import fi.netorek.smsmediator.msgrouting.eip.SmsTextParser;
import fi.netorek.smsmediator.msgrouting.eip.SpringTenantRouteResolver;
import fi.netorek.smsmediator.msgrouting.eip.TenantRouteResolver;

@Configuration
@IntegrationComponentScan
public class IntegrationConfig {
    @Value("${smsmediator.inbound.queue}")
    private String inboundQueueName;

    @Bean
    public IntegrationFlow rabbitMqFlow(ConnectionFactory rabbitConnectionFactory) {
        return IntegrationFlows
                .from(Amqp.inboundAdapter(rabbitConnectionFactory, inboundQueueName))
                .transform(protobufTransformer())
                .transform(routingKeyTransformer())
                .handle(System.out::println)
                .get();
    }

    @Bean
    public ProtobufTransformer protobufTransformer() {
        return new ProtobufTransformer();
    }

    @Bean
    public RouteKeyTransformer routingKeyTransformer() {
        return new RouteKeyTransformer(smsTextParser(), tenantRouteResolver());
    }

    @Bean
    public TenantRouteResolver tenantRouteResolver() {
        return new SpringTenantRouteResolver();
    }

    @Bean
    public SmsTextParser smsTextParser() {
        return new SimpleSmsTextParser();
    }

}
