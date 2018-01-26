package fi.netorek.smsmediator.msgrouting.msg;

import com.google.i18n.phonenumbers.Phonenumber;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class TenantAppMessage {
    private Phonenumber.PhoneNumber phoneNumber;
    private String origin;
    private String routeKey;
    private String text;
    private String tenant;
    private String applicationName;

}
