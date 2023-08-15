package z.note.lite.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SysUserDto {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String username;

}
