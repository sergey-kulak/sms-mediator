package fi.netorek.smsmediator.msgrouting.transform.sms;


import org.junit.Assert;
import org.junit.Test;

import fi.netorek.smsmediator.msgrouting.exception.IncorrectSmsTextFormatException;
import fi.netorek.smsmediator.msgrouting.transform.sms.SimpleSmsTextParser;
import fi.netorek.smsmediator.msgrouting.transform.sms.SmsText;

public class SimpleSmsTextParserTest {
    @Test
    public void testCorrectFormatParsing() {
        String expectedRouteKey = "K1";
        String expectedText = "Hello world";

        SmsText smsText = new SimpleSmsTextParser().parse(expectedRouteKey + "   " + expectedText);

        Assert.assertEquals(expectedRouteKey, smsText.getRouteKey());
        Assert.assertEquals(expectedText, smsText.getText());
    }

    @Test(expected = IncorrectSmsTextFormatException.class)
    public void testInCorrectFormatParsing() {
        new SimpleSmsTextParser().parse("notparsedtext");
    }

}