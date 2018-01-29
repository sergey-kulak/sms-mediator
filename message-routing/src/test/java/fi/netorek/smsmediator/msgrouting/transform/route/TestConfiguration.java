package fi.netorek.smsmediator.msgrouting.transform.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

    @Bean
    public TenantRouteResolver tenantRouteResolver() {
        return new SpringTenantRouteResolver();
    }
}