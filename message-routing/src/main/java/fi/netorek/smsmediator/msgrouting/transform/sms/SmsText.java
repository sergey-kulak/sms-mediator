package fi.netorek.smsmediator.msgrouting.transform.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SmsText {
    private String routeKey;
    private String text;
}
