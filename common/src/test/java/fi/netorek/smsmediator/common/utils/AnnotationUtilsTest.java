package fi.netorek.smsmediator.common.utils;

import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Component;

public class AnnotationUtilsTest {
    @Test
    public void testFindAnnotatedClasses() {
        Set<Class> classes = AnnotationUtils.findAnnotatedClasses("fi.netorek.smsmediator.common.utils", Component.class);
        Assert.assertFalse(classes.isEmpty());
        Assert.assertTrue(classes.contains(TestComponent.class));
    }

}