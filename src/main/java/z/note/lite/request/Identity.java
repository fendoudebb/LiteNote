package z.note.lite.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Identity {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String captcha;

}
