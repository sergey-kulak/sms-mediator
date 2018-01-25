package fi.netorek.smsmediator.msgrouting.transform.route;

public class TenantRoute {
    private String tenant;
    private String application;

    public TenantRoute(String tenant, String application) {
        this.tenant = tenant;
        this.application = application;
    }

    public String getTenant() {
        return tenant;
    }

    public String getApplication() {
        return application;
    }
}
