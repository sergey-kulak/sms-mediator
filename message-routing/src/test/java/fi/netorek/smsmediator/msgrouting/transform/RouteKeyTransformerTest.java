package fi.netorek.smsmediator.msgrouting.transform;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.Message;

import fi.netorek.smsmediator.msgrouting.msg.TenantAppMessage;
import fi.netorek.smsmediator.msgrouting.transform.route.TenantRoute;
import fi.netorek.smsmediator.msgrouting.transform.route.TenantRouteResolver;
import fi.netorek.smsmediator.msgrouting.transform.sms.SimpleSmsTextParser;
import fi.netorek.smsmediator.msgrouting.transform.sms.SmsTextParser;
import fi.netorek.smsmediator.msgrouting.utils.TestEntityHelper;
import fi.netorek.smsmediator.proto.InboundMessage;

@RunWith(MockitoJUnitRunner.class)
public class RouteKeyTransformerTest {
    @Spy
    private SmsTextParser smsTextParser = new SimpleSmsTextParser();
    @Mock
    private TenantRouteResolver tenantRouteResolver;
    @InjectMocks
    private RouteKeyTransformer routeKeyTransformer;

    @Test
    public void testTransform() {
        String expectedRoutingKey = "K1";
        String expectedTenant = "k1-tenant";
        String expectedApp = "k1-app";
        String expectedText = "sms text";

        String smsBody = expectedRoutingKey + " " + expectedText;
        InboundMessage.Message message = TestEntityHelper.buildInboundMessage(smsBody);

        when(tenantRouteResolver.resolve(expectedRoutingKey)).thenReturn(new TenantRoute(expectedTenant, expectedApp));
        Message result = routeKeyTransformer.transform(TestEntityHelper.buildMessage(message));

        verify(smsTextParser, times(1)).parse(smsBody);
        verify(tenantRouteResolver, times(1)).resolve(expectedRoutingKey);

        TenantAppMessage resultPayload = (TenantAppMessage) result.getPayload();
        assertThat(resultPayload, allOf(
                hasProperty("phoneNumber", is(message.getPhoneNumber())),
                hasProperty("origin", is(message.getOrigin())),
                hasProperty("routeKey", is(expectedRoutingKey)),
                hasProperty("text", is(expectedText)),
                hasProperty("tenant", is(expectedTenant)),
                hasProperty("applicationName", is(expectedApp))
        ));
    }

}