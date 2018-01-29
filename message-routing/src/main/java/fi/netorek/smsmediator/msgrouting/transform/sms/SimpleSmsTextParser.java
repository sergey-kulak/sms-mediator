package fi.netorek.smsmediator.msgrouting.transform.sms;

import fi.netorek.smsmediator.msgrouting.exception.IncorrectSmsTextFormatException;

/**
 * The parser to process sms text in following template: <br>
 * &lt;logical routing key&gt;&lt;one or more spaces&gt;&lt;text itself&gt;<br>
 * Example: "K1 Hello World"
 */
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
