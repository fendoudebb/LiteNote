package z.note.lite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class LiteNoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiteNoteApplication.class, args);
    }

}
