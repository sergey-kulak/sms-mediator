package fi.netorek.smsmediator.msgrouting.transform.sms;

/**
 * Generic interface for any sms text parsing logic extracting logical routing key and sms text itself
 */
public interface SmsTextParser {
    SmsText parse(String rawText);
}
