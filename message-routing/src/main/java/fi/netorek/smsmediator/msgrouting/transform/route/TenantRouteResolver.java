package fi.netorek.smsmediator.msgrouting.transform.route;

/**
 * Generic interface to resolve logical routing key to specific tenant information
 */
public interface TenantRouteResolver {
    TenantRoute resolve(String key);
}
