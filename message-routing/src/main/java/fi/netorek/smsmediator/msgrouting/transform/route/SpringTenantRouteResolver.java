package fi.netorek.smsmediator.msgrouting.transform.route;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import fi.netorek.smsmediator.msgrouting.exception.TenantRouteNotFoundException;

/**
 * Tenant route resolver keeping mapping between logical routing key and tenant info in spring config. <br>
 * Format is following:<br>
 *     msg-routing.mapping.&lt;logical routing key&gt;.tenant=&lt;tenant name&gt;<br>
 *     msg-routing.mapping.&lt;logical routing key&gt;.app=&lt;tenant application name&gt;
 */
public class SpringTenantRouteResolver implements TenantRouteResolver, EnvironmentAware {
    private static final String TENANT_SUFFIX = ".tenant";
    private static final String APP_SUFFIX = ".app";

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public TenantRoute resolve(String routeKey) {
        String tenant = propertyResolver.getProperty(getTenantKey(routeKey));
        String app = propertyResolver.getProperty(getAppKey(routeKey));
        if (tenant == null || app == null) {
            throw new TenantRouteNotFoundException(routeKey);
        }
        return new TenantRoute(tenant, app);
    }

    private String getTenantKey(String routeKey) {
        return routeKey + TENANT_SUFFIX;
    }

    private String getAppKey(String routeKey) {
        return routeKey + APP_SUFFIX;
    }

    @Override
    public void setEnvironment(Environment environment) {
        propertyResolver = new RelaxedPropertyResolver(environment, "msg-routing.mapping.");
    }
}
