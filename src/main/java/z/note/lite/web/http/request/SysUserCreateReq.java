package z.note.lite.web.http.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SysUserCreateReq {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String createBy;

}
