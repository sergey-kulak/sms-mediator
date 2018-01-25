package fi.netorek.smsmediator.msgrouting.msg;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class TenantAppMessage {
    private String phoneNumber;
    private String origin;
    private String routeKey;
    private String text;
    private String tenant;
    private String applicationName;

}
