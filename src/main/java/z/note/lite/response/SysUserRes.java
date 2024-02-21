package z.note.lite.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SysUserRes {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String username;

}
