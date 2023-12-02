package z.note.lite.web.http.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LinkModifyReq {

    @NotNull
    private Integer id;

    @NotBlank
    private String website;

    @NotBlank
    private String url;

    @NotBlank
    private String webmaster;

    private String webmasterEmail;

    @NotNull
    private Integer sort;

}
