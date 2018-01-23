package fi.netorek.smsmediator.webapp;

import java.io.IOException;
import java.util.Set;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import fi.netorek.smsmediator.webapp.utils.AnnotationUtils;

@SpringBootApplication
public class SmsMediatorApplication {
    private static final String BASE_PACKAGE = "fi.netorek.smsmediator";

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() throws IOException {
        PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertyConfigurer.setLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/config/*.properties"));
        return propertyConfigurer;
    }

    public static void main(String[] args) {
        Set<Class> applicaiotnClasses = AnnotationUtils.findAnnotatedClasses(BASE_PACKAGE, SpringBootApplication.class);
        SpringApplication.run(applicaiotnClasses.toArray(), args);
    }

}