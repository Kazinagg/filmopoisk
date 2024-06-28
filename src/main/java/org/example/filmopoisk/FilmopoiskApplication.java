package org.example.filmopoisk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@RestController
@EnableFeignClients
public class FilmopoiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmopoiskApplication.class, args);
    }

}
