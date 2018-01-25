package fi.netorek.smsmediator.webapp;

import java.util.Set;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import fi.netorek.smsmediator.common.utils.AnnotationUtils;

@SpringBootApplication
public class SmsMediatorApplication extends SpringBootServletInitializer {
    private static final String BASE_PACKAGE = "fi.netorek.smsmediator";

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(getApplicationClasses());
    }

    public static void main(String[] args) {
        SpringApplication.run(getApplicationClasses(), args);
    }

    private static Object[] getApplicationClasses() {
        Set<Class> applicationClasses =
                AnnotationUtils.findAnnotatedClasses(BASE_PACKAGE, SpringBootApplication.class);
        return applicationClasses.toArray();
    }

}