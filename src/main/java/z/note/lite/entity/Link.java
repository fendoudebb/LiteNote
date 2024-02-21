package z.note.lite.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Setter
@Getter
@ToString
public class Link {

    private Integer id;

    private String website;

    private String url;

    private String webmaster;

    private String webmasterEmail;

    private int status;

    private String sort;

    private OffsetDateTime createTs;

    private OffsetDateTime updateTs;

}
