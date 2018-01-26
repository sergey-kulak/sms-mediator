package fi.netorek.smsmediator.msgrouting.transform;

import com.google.i18n.phonenumbers.Phonenumber;
import fi.netorek.smsmediator.msgrouting.transform.phone.PhoneNumberParser;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import fi.netorek.smsmediator.msgrouting.msg.TenantAppMessage;
import fi.netorek.smsmediator.msgrouting.transform.route.TenantRoute;
import fi.netorek.smsmediator.msgrouting.transform.route.TenantRouteResolver;
import fi.netorek.smsmediator.msgrouting.transform.sms.SmsText;
import fi.netorek.smsmediator.msgrouting.transform.sms.SmsTextParser;
import fi.netorek.smsmediator.proto.InboundMessage;

public class RouteKeyTransformer implements Transformer {
    private TenantRouteResolver tenantRouteResolver;
    private SmsTextParser smsTextParser;
    private PhoneNumberParser phoneNumberParser;

    public RouteKeyTransformer(SmsTextParser smsTextParser, TenantRouteResolver tenantRouteResolver, PhoneNumberParser phoneNumberParser) {
        this.smsTextParser = smsTextParser;
        this.tenantRouteResolver = tenantRouteResolver;
        this.phoneNumberParser = phoneNumberParser;
    }

    @Override
    public Message<?> transform(Message<?> message) {
        InboundMessage.Message payload = (InboundMessage.Message) message.getPayload();
        SmsText smsText = smsTextParser.parse(payload.getBody());
        TenantRoute tenantRoute = tenantRouteResolver.resolve(smsText.getRouteKey());
        Phonenumber.PhoneNumber phoneNumber = phoneNumberParser.parse(payload.getPhoneNumber());
        TenantAppMessage appMessage = buildTenantAppMessage(payload, smsText, tenantRoute, phoneNumber);

        return MessageBuilder.withPayload(appMessage)
                .copyHeaders(message.getHeaders())
                .build();
    }

    private TenantAppMessage buildTenantAppMessage(InboundMessage.Message payload, SmsText smsText,
                                                   TenantRoute tenantRoute, Phonenumber.PhoneNumber phoneNumber) {
        return TenantAppMessage.builder()
                .phoneNumber(phoneNumber)
                .origin(payload.getOrigin())
                .routeKey(smsText.getRouteKey())
                .text(smsText.getText())
                .tenant(tenantRoute.getTenant())
                .applicationName(tenantRoute.getApplication())
                .build();
    }


}
