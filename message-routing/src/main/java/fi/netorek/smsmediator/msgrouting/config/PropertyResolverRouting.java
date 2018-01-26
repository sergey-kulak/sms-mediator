package fi.netorek.smsmediator.msgrouting.config;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

public class PropertyResolverRouting implements EnvironmentAware {
    private static final String TENANT_SUFFIX = ".tenant";
    private static final String APP_SUFFIX = ".app";

    private RelaxedPropertyResolver propertyResolver;

    public String getProperty(String key) {
        return propertyResolver.getProperty(key);
    }

    public String getTenantKey(String routeKey) {
        return routeKey + TENANT_SUFFIX;
    }

    public String getAppKey(String routeKey) {
        return routeKey + APP_SUFFIX;
    }

    @Override
    public void setEnvironment(Environment environment) {
        propertyResolver = new RelaxedPropertyResolver(environment, "smsmediator.routing.");
    }
}
