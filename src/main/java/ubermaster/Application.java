package ubermaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.Locale;
import java.util.logging.LogManager;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) throws IOException {
        Locale.setDefault(new Locale("en","EN"));
        try {
            LogManager.getLogManager().readConfiguration(
                    Application.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
        SpringApplication.run(Application.class,args);
    }

    @Bean
    public WebMvcConfigurer corsConfig(){
       return new WebMvcConfigurerAdapter(){
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedOrigins("http://localhost:8090","http://localhost:4200");
            }
        };
    }
}
