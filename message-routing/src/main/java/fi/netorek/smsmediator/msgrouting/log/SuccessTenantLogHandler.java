package fi.netorek.smsmediator.msgrouting.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.messaging.Message;

import fi.netorek.smsmediator.msgrouting.msg.TenantAppMessage;

public class SuccessTenantLogHandler {
    private static final Logger logger = LoggerFactory.getLogger(SuccessTenantLogHandler.class);
    private static final String TENANT_LOG_KEY = "tenant";

    public void handle(Message<TenantAppMessage> message) {
        try {
            MDC.put(TENANT_LOG_KEY, message.getPayload().getTenant());
            logger.info(message.toString());
        } finally {
            MDC.remove(TENANT_LOG_KEY);
        }
    }
}
