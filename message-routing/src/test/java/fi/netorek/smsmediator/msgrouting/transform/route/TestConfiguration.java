package fi.netorek.smsmediator.msgrouting.transform.route;

import fi.netorek.smsmediator.msgrouting.config.PropertyResolverRouting;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {
    @Bean
    public PropertyResolverRouting propertyResolverRouting() {
        return new PropertyResolverRouting();
    }

    @Bean
    public TenantRouteResolver tenantRouteResolver() {
        return new SpringTenantRouteResolver(propertyResolverRouting());
    }
}