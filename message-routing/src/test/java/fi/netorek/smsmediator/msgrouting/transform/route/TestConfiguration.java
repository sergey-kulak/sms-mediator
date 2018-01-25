package fi.netorek.smsmediator.msgrouting.transform.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fi.netorek.smsmediator.msgrouting.transform.route.SpringTenantRouteResolver;
import fi.netorek.smsmediator.msgrouting.transform.route.TenantRouteResolver;

@Configuration
public class TestConfiguration {
    @Bean
    public TenantRouteResolver tenantRouteResolver() {
        return new SpringTenantRouteResolver();
    }
}