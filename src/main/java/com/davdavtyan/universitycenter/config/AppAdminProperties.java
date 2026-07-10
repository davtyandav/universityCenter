package com.davdavtyan.universitycenter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app.admin")
public class AppAdminProperties {
    
    private String email;
    private String password;

}