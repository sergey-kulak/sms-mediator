package fi.netorek.smsmediator.msgrouting.eip;

import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import fi.netorek.smsmediator.msgrouting.msg.TenantAppMessage;

public class RouteKeyTransformer implements Transformer {
    private TenantRouteResolver tenantRouteResolver;
    private SmsTextParser smsTextParser;

    public RouteKeyTransformer(SmsTextParser smsTextParser, TenantRouteResolver tenantRouteResolver) {
        this.smsTextParser = smsTextParser;
        this.tenantRouteResolver = tenantRouteResolver;
    }

    @Override
    public Message<?> transform(Message<?> message) {
        SmsText smsText = smsTextParser.parse(message.getPayload().toString());
        TenantRoute tenantRoute = tenantRouteResolver.resolve(smsText.getRouteKey());
        TenantAppMessage appMessage = new TenantAppMessage(smsText, tenantRoute);

        return MessageBuilder.withPayload(appMessage)
                .copyHeaders(message.getHeaders())
                .build();
    }


}
