package ubermaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ubermaster.persistence.facade.DB_Cleaner;

import java.io.IOException;
import java.util.Locale;


@SpringBootApplication
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) throws IOException {
        Locale.setDefault(new Locale("en","EN"));
        SpringApplication.run(Application.class,args);

        /*DB_Cleaner db_cleaner = new DB_Cleaner(10000);
        db_cleaner.start();*/
    }

    @Bean
    public WebMvcConfigurer corsConfig(){
       return new WebMvcConfigurerAdapter(){
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
            }
        };
    }
}
