package com.example.demo.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatHttpConfig {
    @Value("${http.port}")
    private Integer port;
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcatServletWebServerFactory =new TomcatServletWebServerFactory();
    Connector connector =new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setPort(port);
    tomcatServletWebServerFactory.addAdditionalTomcatConnectors(connector);
    return tomcatServletWebServerFactory;
    }
}
