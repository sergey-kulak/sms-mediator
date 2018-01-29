# sms-mediator

Sms mediator containts of 4 modules:
 - common (for common utility and protobuf messages)
 - message-receiver (module recieving messages from SMS gateways)
 - message-routing (a module for processing and rotuing messages)
 - webapp (module including all modules for single unit deployment)
 
Message-receiver, message-routing and webapp are Spring boot executable war. They can be run as a jar or be deployed as a war to servlet container if original (not modified by Spring Boot plugin) war is used.

To run the system, you must specify --spring.config.location option with all necessary configuration. You can use config/*.yml files as example
