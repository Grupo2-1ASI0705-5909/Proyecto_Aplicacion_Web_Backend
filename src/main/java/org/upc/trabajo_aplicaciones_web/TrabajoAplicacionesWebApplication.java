package org.upc.trabajo_aplicaciones_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrabajoAplicacionesWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrabajoAplicacionesWebApplication.class, args);
    }

}
