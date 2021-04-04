package com.oneworldaccuracy.userservice.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */


@Data
@Component
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "app")
public class ApplicationProperties {

    private final ApplicationProperties.Mail mail = new ApplicationProperties.Mail();

    @Data
    public static class Mail {
        private String from = "";
        private String baseUrl = "";
    }
}
