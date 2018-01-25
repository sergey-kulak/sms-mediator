package fi.netorek.smsmediator.msgrouting.transform.sms;

public interface SmsTextParser {
    SmsText parse(String rawText);
}
