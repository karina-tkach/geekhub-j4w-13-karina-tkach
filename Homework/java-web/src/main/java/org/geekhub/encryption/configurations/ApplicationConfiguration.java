package org.geekhub.encryption.configurations;

import org.geekhub.ciphers.A1Z26Cipher;
import org.geekhub.ciphers.AtbashCipher;
import org.geekhub.ciphers.CaesarCipher;
import org.geekhub.ciphers.VigenereCipher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("org.geekhub.encryption")
@PropertySource("classpath:/application.properties")
public class ApplicationConfiguration {
    @Bean
    public CaesarCipher caesarCipher(@Value("${caesar.key}") int caesarKey) {
        return new CaesarCipher(caesarKey);
    }

    @Bean
    public AtbashCipher atbashCipher() {
        return new AtbashCipher();
    }

    @Bean
    public A1Z26Cipher a1Z26Cipher() {
        return new A1Z26Cipher();
    }

    @Bean
    public VigenereCipher vigenereCipher(@Value("${vigenere.key}") String key) {
        return new VigenereCipher(key);
    }
}
