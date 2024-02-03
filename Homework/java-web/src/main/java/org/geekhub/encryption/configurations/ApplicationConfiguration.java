package org.geekhub.encryption.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("org.geekhub.encryption")
@PropertySource("classpath:/application.properties")
public class ApplicationConfiguration {
}
