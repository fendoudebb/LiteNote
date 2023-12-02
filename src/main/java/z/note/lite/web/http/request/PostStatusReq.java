package z.note.lite.web.http.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostStatusReq {

    @NotNull
    private Integer postId;

    @NotNull
    private Short status;

}
