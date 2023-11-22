package z.note.lite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.core.metrics.jfr.FlightRecorderApplicationStartup;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan
public class LiteNoteApplication {

    public static void main(String[] args) {
//        SpringApplication.run(LiteNoteApplication.class, args);
        SpringApplication app = new SpringApplication(LiteNoteApplication.class);
//        app.setApplicationStartup(new BufferingApplicationStartup(2048));
        app.setApplicationStartup(new FlightRecorderApplicationStartup());
        app.run(args);
    }

}
