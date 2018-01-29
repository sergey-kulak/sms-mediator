package fi.netorek.smsmediator.msgrouting.transform.route;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TenantRoute {
    private String tenant;
    private String application;
}
