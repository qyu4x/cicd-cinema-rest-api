package com.coffekyun.cinema.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cinema RESTful API")
                        .description("OpenAPI for Cinema RESTful API")
                        .version("1.0.1")
                        .contact(
                                new Contact()
                                        .name("Ariq Khoiri")
                                        .email("qq.khoiri@gmail.com")
                                        .url("github.com/qyu4x")
                        )
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                        .externalDocs(new ExternalDocumentation()
                        .description("github Cinema RESTful API")
                        .url("github.com/qyu4x/"))
                        .servers(servers())

                ;
    }

    private List<Server> servers() {
        List<Server> servers = new ArrayList<>();

        Server serverDevFirst = new Server();
        serverDevFirst.setUrl("http://localhost:8080/");
        serverDevFirst.setDescription("Main server for Dev");

        Server serverDevSecond = new Server();
        serverDevSecond.setUrl("https://dev.coffekyun.com/api/v1");
        serverDevSecond.setDescription("Second server for Dev");

        Server serverQA = new Server();
        serverQA.setUrl("https://qa.coffekyun.com/api/v1");
        serverQA.setDescription("Server for QA");

        Server serverProduction = new Server();
        serverProduction.setUrl("https://prod.coffekyun.com/api/v1");
        serverProduction.setDescription("Server for Production");

        servers.add(serverDevFirst);
        servers.add(serverProduction);
        servers.add(serverDevSecond);
        servers.add(serverQA);


        return servers;
    }

}
