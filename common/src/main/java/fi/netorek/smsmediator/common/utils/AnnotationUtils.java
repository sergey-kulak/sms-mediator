package fi.netorek.smsmediator.common.utils;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public final class AnnotationUtils {
    private AnnotationUtils() {
    }

    /**
     * Scan a package and its all sub-packages to find all classes annotated with specified annotation
     *
     * @param scanPackage    package to scan
     * @param annotationType annotation for search
     * @return set of classes matched annotation search
     */
    public static Set<Class> findAnnotatedClasses(String scanPackage, Class<? extends Annotation> annotationType) {
        ClassPathScanningCandidateComponentProvider provider
                = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(annotationType));
        Set<BeanDefinition> beans = provider.findCandidateComponents(scanPackage);
        return beans.stream().map(b -> getClass(b.getBeanClassName())).collect(Collectors.toSet());
    }

    private static Class getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
