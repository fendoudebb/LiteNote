package z.note.lite.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public record SysUserDto(
        @JsonSerialize(using = ToStringSerializer.class)
        Long id,

        String username) {

}
