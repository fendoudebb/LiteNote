package z.note.lite.web.model.common;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Ip {

    @Id
    private Integer id;

    private String ip;

    private String country;

}
