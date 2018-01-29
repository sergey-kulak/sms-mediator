package fi.netorek.smsmediator.msgrouting.transform;

import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import fi.netorek.smsmediator.msgrouting.exception.IncorrectPhoneNumberFormatException;
import fi.netorek.smsmediator.msgrouting.transform.route.TenantRoute;
import fi.netorek.smsmediator.msgrouting.transform.route.TenantRouteResolver;
import fi.netorek.smsmediator.msgrouting.transform.sms.SmsText;
import fi.netorek.smsmediator.msgrouting.transform.sms.SmsTextParser;
import fi.netorek.smsmediator.proto.InboundMessage;
import fi.netorek.smsmediator.proto.TenantAppMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * The transformer performing validation and enrichment of Message with InboundMessage.Message payload
 */
public class RouteKeyTransformer implements Transformer {
    private TenantRouteResolver tenantRouteResolver;
    private SmsTextParser smsTextParser;
    @Getter
    @Setter
    private String defaultPhoneRegion;

    public RouteKeyTransformer(SmsTextParser smsTextParser, TenantRouteResolver tenantRouteResolver,
                               String defaultPhoneRegion) {
        this.smsTextParser = smsTextParser;
        this.tenantRouteResolver = tenantRouteResolver;
        this.defaultPhoneRegion = defaultPhoneRegion;
    }

    @Override
    public Message<?> transform(Message<?> message) {
        InboundMessage.Message payload = (InboundMessage.Message) message.getPayload();
        validatePhoneNumber(payload.getPhoneNumber());
        SmsText smsText = smsTextParser.parse(payload.getBody());
        TenantRoute tenantRoute = tenantRouteResolver.resolve(smsText.getRouteKey());
        TenantAppMessage.Message appMessage = buildTenantAppMessage(payload, smsText, tenantRoute);

        return MessageBuilder.withPayload(appMessage)
                .copyHeaders(message.getHeaders())
                .build();
    }

    private void validatePhoneNumber(String rawText) {
        try {
            PhoneNumberUtil.getInstance().parse(rawText, defaultPhoneRegion);
        } catch (NumberParseException ex) {
            throw new IncorrectPhoneNumberFormatException(ex);
        }
    }

    private TenantAppMessage.Message buildTenantAppMessage(InboundMessage.Message payload, SmsText smsText,
                                                           TenantRoute tenantRoute) {
        return TenantAppMessage.Message.newBuilder()
                .setPhoneNumber(payload.getPhoneNumber())
                .setOrigin(payload.getOrigin())
                .setRouteKey(smsText.getRouteKey())
                .setText(smsText.getText())
                .setTenant(tenantRoute.getTenant())
                .setApplicationName(tenantRoute.getApplication())
                .build();
    }


}
