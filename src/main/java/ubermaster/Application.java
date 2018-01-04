package ubermaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Locale;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
        Locale.setDefault(new Locale("en","EN"));
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
