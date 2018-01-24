package fi.netorek.smsmediator.msgreceiver.controller;

import fi.netorek.smsmediator.proto.InboundMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MsgReceiverController {

    @RequestMapping(value="/send/request", method= RequestMethod.GET)
    public ResponseEntity orderBrokerLocateItems(@RequestParam("phoneNumber") String phoneNumber,
                                         @RequestParam("body") String body, @RequestParam("origin") String origin) {
        try {
            InboundMessage.Message inboundMessage = InboundMessage.Message.newBuilder().
                    setPhoneNumber(phoneNumber).
                    setBody(body).
                    setOrigin(origin).build();


            ApplicationContext context = new GenericXmlApplicationContext("classpath:/spring/rabbit-context.xml");

            AmqpTemplate template = context.getBean(AmqpTemplate.class);

            template.convertAndSend("inboundMessagesQueue", inboundMessage);


            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
