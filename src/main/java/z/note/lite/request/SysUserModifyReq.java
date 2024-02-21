package z.note.lite.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SysUserModifyReq {

    @NotNull
    private Long id;

    @NotNull
    private String password;

    private String updateBy;

}
