package fi.netorek.smsmediator.msgrouting;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestBean2 {
    @Value("${test2.value}")
    private int value;

    @PostConstruct
    public void afterInit() {
        System.out.println(value);
    }
}
