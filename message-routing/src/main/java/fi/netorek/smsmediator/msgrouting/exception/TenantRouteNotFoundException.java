package fi.netorek.smsmediator.msgrouting.exception;

public class TenantRouteNotFoundException extends RuntimeException {
    private static final String MSG_TEMPLATE = "Tenant route not found for '%s' key";

    public TenantRouteNotFoundException(String routeKey) {
        super(String.format(MSG_TEMPLATE, routeKey));
    }
}
