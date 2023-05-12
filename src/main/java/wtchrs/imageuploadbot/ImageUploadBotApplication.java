package wtchrs.imageuploadbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ImageUploadBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageUploadBotApplication.class, args);
    }

}
