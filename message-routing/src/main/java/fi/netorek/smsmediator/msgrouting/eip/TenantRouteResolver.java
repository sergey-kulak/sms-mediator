package fi.netorek.smsmediator.msgrouting.eip;

public interface TenantRouteResolver {
    TenantRoute resolve(String key);
}
