package fi.netorek.smsmediator.msgreceiver.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

public class PropertyResolver implements EnvironmentAware {

    private static final String PROP_NAME_SUFFIX = ".property.name";

    @Getter
    @AllArgsConstructor
    public enum Property {
        PHONE("phone"),
        BODY("body"),
        ORIGIN("origin");

        private String name;
    }

    private RelaxedPropertyResolver propertyResolver;


    public String getProperty(Property property) {
        return propertyResolver.getProperty(property.getName() + PROP_NAME_SUFFIX);
    }

    @Override
    public void setEnvironment(Environment environment) {
        propertyResolver = new RelaxedPropertyResolver(environment, "smsmediator.receiver.");
    }

}
