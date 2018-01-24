package fi.netorek.smsmediator.msgrouting.msg;

import fi.netorek.smsmediator.msgrouting.eip.SmsText;
import fi.netorek.smsmediator.msgrouting.eip.TenantRoute;

public class TenantAppMessage {
    private String routeKey;
    private String text;
    private String tenant;
    private String applicationName;

    public TenantAppMessage(String routeKey, String text, String tenant, String applicationName) {
        this.text = text;
        this.routeKey = routeKey;
        this.tenant = tenant;
        this.applicationName = applicationName;
    }

    public TenantAppMessage(SmsText smsText, TenantRoute tenantRoute) {
        this(smsText.getRouteKey(), smsText.getText(), tenantRoute.getTenant(), tenantRoute.getApplication());
    }

    public String getText() {
        return text;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public String getTenant() {
        return tenant;
    }

    public String getApplicationName() {
        return applicationName;
    }
}
