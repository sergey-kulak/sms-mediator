package fi.netorek.smsmediator.msgrouting.transform;

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
        TenantAppMessage appMessage = buildTenantAppMessage(smsText, tenantRoute);

        return MessageBuilder.withPayload(appMessage)
                .copyHeaders(message.getHeaders())
                .build();
    }

    private TenantAppMessage buildTenantAppMessage(SmsText smsText, TenantRoute tenantRoute) {
        return new TenantAppMessage(smsText.getRouteKey(), smsText.getText(),
                tenantRoute.getTenant(), tenantRoute.getApplication());
    }


}
