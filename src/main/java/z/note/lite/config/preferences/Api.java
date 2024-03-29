package z.note.lite.config.preferences;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "preferences.api")
public class Api {

    private String login;

    private String captcha;

}
