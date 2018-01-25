package fi.netorek.smsmediator.msgrouting.transform.sms;

import fi.netorek.smsmediator.msgrouting.exception.IncorrectSmsTextFormatException;

public class SimpleSmsTextParser implements SmsTextParser {
    private static final int TOKEN_LENGTH = 2;

    @Override
    public SmsText parse(String rawText) {
        String[] textParts = rawText.split(" ", TOKEN_LENGTH);
        if (textParts.length != TOKEN_LENGTH) {
            throw new IncorrectSmsTextFormatException(rawText);
        }
        return new SmsText(textParts[0].trim(), textParts[1].trim());
    }
}
