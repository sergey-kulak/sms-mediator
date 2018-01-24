package fi.netorek.smsmediator.msgrouting.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import fi.netorek.smsmediator.msgrouting.log.ErrorLogHandler;
import fi.netorek.smsmediator.msgrouting.transform.ProtobufTransformer;
import fi.netorek.smsmediator.msgrouting.transform.RouteKeyTransformer;
import fi.netorek.smsmediator.msgrouting.transform.SimpleSmsTextParser;
import fi.netorek.smsmediator.msgrouting.transform.SmsTextParser;
import fi.netorek.smsmediator.msgrouting.transform.SpringTenantRouteResolver;
import fi.netorek.smsmediator.msgrouting.transform.TenantRouteResolver;
import fi.netorek.smsmediator.msgrouting.log.SuccessTenantLogHandler;
import fi.netorek.smsmediator.msgrouting.msg.TenantAppMessage;

@Configuration
@IntegrationComponentScan
public class IntegrationConfig {
    @Value("${smsmediator.inbound-queue}")
    private String inboundQueueName;

    @Bean
    public IntegrationFlow rabbitMqFlow(ConnectionFactory rabbitConnectionFactory) {
        return IntegrationFlows
                .from(Amqp.inboundAdapter(rabbitConnectionFactory, inboundQueueName)
                        .errorChannel(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME))
                .transform(protobufTransformer())
                .transform(routingKeyTransformer())
                .channel("loggingChannel")
                .get();
    }

    @Bean
    public MessageChannel loggingChannel() {
        return MessageChannels
                .publishSubscribe()
                .datatype(TenantAppMessage.class)
                .get();
    }

    @Bean
    public IntegrationFlow loggingFlow(MessageChannel loggingChannel) {
        return IntegrationFlows
                .from(loggingChannel)
                .handle("successTenantLoggerHandler", "handle")
                .get();
    }

    @Bean
    public IntegrationFlow errorLoggingFlow(MessageChannel errorChannel) {
        return IntegrationFlows
                .from(errorChannel)
                .handle(errorLogHandler())
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

    @Bean
    public SuccessTenantLogHandler successTenantLoggerHandler(){
        return new SuccessTenantLogHandler();
    }

    @Bean
    public ErrorLogHandler errorLogHandler(){
        return new ErrorLogHandler();
    }

}
