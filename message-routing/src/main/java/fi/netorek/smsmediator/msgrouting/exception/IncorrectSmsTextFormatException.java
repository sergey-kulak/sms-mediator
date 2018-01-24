package fi.netorek.smsmediator.msgrouting.exception;

public class IncorrectSmsTextFormatException extends RuntimeException {
    private static final String MSG_TEMPLATE = "Incorrect sms text format: %s";

    public IncorrectSmsTextFormatException(String rawText) {
        super(String.format(MSG_TEMPLATE, rawText));
    }
}
