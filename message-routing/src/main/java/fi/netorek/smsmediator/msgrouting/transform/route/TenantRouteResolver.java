package fi.netorek.smsmediator.msgrouting.transform.route;

public interface TenantRouteResolver {
    TenantRoute resolve(String key);
}
