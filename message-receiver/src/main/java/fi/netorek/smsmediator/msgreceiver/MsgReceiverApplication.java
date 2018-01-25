package fi.netorek.smsmediator.msgreceiver;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@SpringBootApplication
public class MsgReceiverApplication {


    public static void main(String[] args) {
        SpringApplication.run(MsgReceiverApplication.class, args);
    }

}