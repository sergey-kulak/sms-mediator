package fi.netorek.smsmediator.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath*:/config/*.properties")
public class SmsMediatorApplication {

    public static void main(String[] args) {
        // TODO pull all Application files via reflection instead of manual listing
        SpringApplication.run(new Object [] {SmsMediatorApplication.class, fi.netorek.smsmediator.msgrouting.MsgRoutingApplication.class}, args);
    }

}