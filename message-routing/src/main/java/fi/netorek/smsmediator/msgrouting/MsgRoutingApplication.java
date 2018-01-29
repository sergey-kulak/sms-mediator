package fi.netorek.smsmediator.msgrouting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class MsgRoutingApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MsgRoutingApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MsgRoutingApplication.class, args);
    }

}