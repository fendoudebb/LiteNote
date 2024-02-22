package z.note.lite.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SysPermission extends Base {

    private String name;

    private String value;

}
