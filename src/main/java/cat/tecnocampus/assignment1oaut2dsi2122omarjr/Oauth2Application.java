package cat.tecnocampus.assignment1oaut2dsi2122omarjr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableOAuth2Client
public class Oauth2Application {


    public static void main(String[] args) {
        SpringApplication.run(Oauth2Application.class, args);
    }


}

