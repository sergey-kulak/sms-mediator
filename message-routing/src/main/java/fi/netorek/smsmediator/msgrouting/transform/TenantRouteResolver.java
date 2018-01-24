package fi.netorek.smsmediator.msgrouting.transform;

public interface TenantRouteResolver {
    TenantRoute resolve(String key);
}
