package fi.netorek.smsmediator.msgrouting.transform;

import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import fi.netorek.smsmediator.msgrouting.msg.TenantAppMessage;
import fi.netorek.smsmediator.proto.InboundMessage;

public class RouteKeyTransformer implements Transformer {
    private TenantRouteResolver tenantRouteResolver;
    private SmsTextParser smsTextParser;

    public RouteKeyTransformer(SmsTextParser smsTextParser, TenantRouteResolver tenantRouteResolver) {
        this.smsTextParser = smsTextParser;
        this.tenantRouteResolver = tenantRouteResolver;
    }

    @Override
    public Message<?> transform(Message<?> message) {
        InboundMessage.Message payload = (InboundMessage.Message) message.getPayload();
        SmsText smsText = smsTextParser.parse(payload.getBody());
        TenantRoute tenantRoute = tenantRouteResolver.resolve(smsText.getRouteKey());
        TenantAppMessage appMessage = buildTenantAppMessage(payload, smsText, tenantRoute);

        return MessageBuilder.withPayload(appMessage)
                .copyHeaders(message.getHeaders())
                .build();
    }

    private TenantAppMessage buildTenantAppMessage(InboundMessage.Message payload, SmsText smsText,
                                                   TenantRoute tenantRoute) {
        return TenantAppMessage.builder()
                .phoneNumber(payload.getPhoneNumber())
                .origin(payload.getOrigin())
                .routeKey(smsText.getRouteKey())
                .text(smsText.getText())
                .tenant(tenantRoute.getTenant())
                .applicationName(tenantRoute.getApplication())
                .build();
    }


}
