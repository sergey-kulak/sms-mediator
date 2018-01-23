package fi.netorek.smsmediator.msgreceiver;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestBean {
    @Value("${test1.value}")
    private int value;

    @PostConstruct
    public void afterInit() {
        System.out.println(value);
    }
}
