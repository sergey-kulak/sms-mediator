package fi.netorek.smsmediator.msgrouting.transform.route;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.netorek.smsmediator.msgrouting.exception.TenantRouteNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource("/test-route-config.properties")
public class SpringTenantRouteResolverTest {
    @Autowired
    private TenantRouteResolver tenantRouteResolver;

    @Test
    public void testExistingCodeResolving() {
        TenantRoute tenantRoute = tenantRouteResolver.resolve("K1");
        assertEquals("k1-tenant", tenantRoute.getTenant());
        assertEquals("K1 app", tenantRoute.getApplication());
    }

    @Test(expected = TenantRouteNotFoundException.class)
    public void testNotExistingCodeResolving() {
        tenantRouteResolver.resolve("K100");
    }


}

