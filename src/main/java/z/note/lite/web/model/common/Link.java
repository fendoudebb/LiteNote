package z.note.lite.web.model.common;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "link")
public class Link {

    @Id
    private Integer id;

    private String website;

    private String url;

    private String webmaster;

    private String webmasterEmail;

    private int status;

}