package ubermaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

import java.util.Locale;


@SpringBootApplication
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
        Locale.setDefault(new Locale("en","EN"));
        SpringApplication.run(Application.class,args);
    }
}
