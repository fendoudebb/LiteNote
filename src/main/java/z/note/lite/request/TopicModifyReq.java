package z.note.lite.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TopicModifyReq {

    @NotNull
    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    private Integer sort;

}
