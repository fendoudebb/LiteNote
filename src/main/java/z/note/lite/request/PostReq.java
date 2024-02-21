package z.note.lite.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostReq {

    private Integer id;

    private Long uid;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotEmpty
    private String topics;

    @NotBlank
    private String content;

    @NotBlank
    private String contentHtml;

    private int wordCount;

    private int prop;

    private int status;

    private int commentStatus;

    private String images;

}
