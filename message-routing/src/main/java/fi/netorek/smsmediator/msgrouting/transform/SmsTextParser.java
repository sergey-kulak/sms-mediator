package fi.netorek.smsmediator.msgrouting.transform;

public interface SmsTextParser {
    SmsText parse(String rawText);
}
