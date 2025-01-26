package org.invest;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableFeignClients
public class Application {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        System.setProperty("POSTGRES_USERNAME", dotenv.get("POSTGRES_USERNAME"));
        System.setProperty("POSTGRES_PASSWORD", dotenv.get("POSTGRES_PASSWORD"));
        System.setProperty("SECRET", dotenv.get("SECRET"));
        System.setProperty("POLYGON_TOKEN", dotenv.get("POLYGON_TOKEN"));

        SpringApplication.run(Application.class, args);
    }
}