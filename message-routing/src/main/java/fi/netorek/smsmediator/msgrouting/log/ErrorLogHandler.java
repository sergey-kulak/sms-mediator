package fi.netorek.smsmediator.msgrouting.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.ErrorMessage;

public class ErrorLogHandler implements MessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(ErrorLogHandler.class);

    @Override
    public void handleMessage(Message<?> message) {
        ErrorMessage errorMessage = (ErrorMessage) message;
        logger.error(String.format("error during processing %s", message), errorMessage.getPayload());
    }
}
