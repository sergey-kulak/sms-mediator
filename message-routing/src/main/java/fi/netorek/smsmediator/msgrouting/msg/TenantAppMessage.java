package fi.netorek.smsmediator.msgrouting.msg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class TenantAppMessage {
    private String routeKey;
    private String text;
    private String tenant;
    private String applicationName;

}
