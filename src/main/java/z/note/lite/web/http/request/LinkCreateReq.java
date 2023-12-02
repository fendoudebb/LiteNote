package z.note.lite.web.http.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LinkCreateReq {

    @NotBlank
    private String website;

    @NotBlank
    private String url;

    @NotBlank
    private String webmaster;

    private String webmasterEmail;

}
