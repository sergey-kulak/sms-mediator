package fi.netorek.smsmediator.msgrouting.transform.route;

import fi.netorek.smsmediator.msgrouting.config.PropertyResolverRouting;
import lombok.AllArgsConstructor;

import fi.netorek.smsmediator.msgrouting.exception.TenantRouteNotFoundException;

@AllArgsConstructor
public class SpringTenantRouteResolver implements TenantRouteResolver {

    PropertyResolverRouting propertyResolver;

    @Override
    public TenantRoute resolve(String routeKey) {
        String tenant = propertyResolver.getProperty(propertyResolver.getTenantKey(routeKey));
        String app = propertyResolver.getProperty(propertyResolver.getAppKey(routeKey));
        if (tenant == null || app == null) {
            throw new TenantRouteNotFoundException(routeKey);
        }
        return new TenantRoute(tenant, app);
    }
}
